package com.dsj.imoveis.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.dsj.imoveis.lib.dto.ImmobileDTO;
import com.dsj.imoveis.lib.dto.ImmobileMinDTO;
import com.dsj.imoveis.lib.entities.Immobile;
import com.dsj.imoveis.lib.enums.*;
import com.dsj.imoveis.mapper.ImmobileMapper;
import com.dsj.imoveis.repository.ImmobileRepository;
import com.dsj.imoveis.service.ImmobileService;
import com.dsj.imoveis.service.S3Service;
import com.dsj.imoveis.service.exceptions.DatabaseException;
import com.dsj.imoveis.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImmobileServiceImpl implements ImmobileService {

    private final ImmobileRepository repository;

    private final ImmobileMapper immobileMapper;

    private final S3Service s3Service;

    private final AmazonS3 amazonS3;

    @Override
    @Transactional
    public ImmobileDTO save(ImmobileDTO dto, List<MultipartFile> files) {

        List<String> imageUrls = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                String imageUrl = s3Service.uploadImage(file);
                imageUrls.add(imageUrl);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer upload das imagens", e);
        }

        ImmobileDTO updatedDto = dto.toBuilder()
                .imageUrls(imageUrls)
                .build();

        Immobile entity = immobileMapper.mapImmobile(updatedDto);
        validateImmobile(entity);
        entity = repository.save(entity);
        return immobileMapper.mapImmobileDTO(entity);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ImmobileDTO findById(Long id) {
        return repository.findById(id)
                .map(immobileMapper::mapImmobileDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found for id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImmobileMinDTO> findAll(
            String title,
            ImmobileCategory category,
            String subtype,
            String city,
            String state,
            String neighborhood,
            Double minPrice,
            Double maxPrice,
            Integer bedrooms,
            String zipCode,
            OptionImmobile option,
            Pageable pageable) {

        Page<Immobile> result = repository.search(title, category, subtype, city, state, neighborhood, minPrice, maxPrice, bedrooms, zipCode, option, pageable);
        return result.map(immobileMapper::mapImmobileMinDTO);
    }

    @Override
    @Transactional
    public ImmobileDTO update(Long id, ImmobileDTO dto, List<MultipartFile> files) {
        try {
            Immobile entity = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Immobile not found"));
            
            List<String> currentImageUrls = new ArrayList<>(entity.getImageUrls());
            List<String> updatedImageUrls = dto.imageUrls() != null ? dto.imageUrls() : new ArrayList<>();

            for (String currentUrl : currentImageUrls) {
                if (!updatedImageUrls.contains(currentUrl)) {
                    String fileName = extractFileNameFromUrl(currentUrl);
                    deleteImageFromS3(fileName);
                }
            }

            if (files != null && !files.isEmpty()) {
                for (MultipartFile file : files) {
                    String imageUrl = s3Service.uploadImage(file);
                    updatedImageUrls.add(imageUrl);
                }
            }
            entity.setImageUrls(updatedImageUrls);

            immobileMapper.updateImmobileFromDTO(dto, entity);
            validateImmobile(entity);

            entity = repository.save(entity);
            return immobileMapper.mapImmobileDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource not found!");
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer upload das imagens", e);
        }
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Resource not found!");
        }

        Immobile immobile = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Immobile not found"));

        if (immobile.getImageUrls() != null) {
            for (String imageUrl : immobile.getImageUrls()) {
                String fileName = extractFileNameFromUrl(imageUrl);
                deleteImageFromS3(fileName);
            }
        }

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential integrity failure!");
        }
    }

    private String extractFileNameFromUrl(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }

    private void deleteImageFromS3(String fileName) {
        String bucketName = "dsj-imoveis-imgs";

        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir a imagem do S3", e);
        }
    }

    private void validateImmobile(Immobile immobile) {
        validateCategoryAndSubtype(immobile);
        validateOption(immobile);
    }

    private void validateCategoryAndSubtype(Immobile immobile) {
        if (immobile.getCategory() == null) {
            throw new IllegalArgumentException("Category must be provided");
        }

        if (immobile.getSubtype() == null || immobile.getSubtype().isEmpty()) {
            throw new IllegalArgumentException("Subtype must be provided");
        }

        try {
            switch (immobile.getCategory()) {
                case RESIDENCIAL:
                    ResidentialType.valueOf(immobile.getSubtype());
                    break;
                case COMERCIAL:
                    CommercialType.valueOf(immobile.getSubtype());
                    break;
                case RURAL:
                    RuralType.valueOf(immobile.getSubtype());
                    break;
                default:
                    throw new IllegalArgumentException("Invalid category: " + immobile.getCategory());
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid subtype '" + immobile.getSubtype() + "' for category " + immobile.getCategory(), e);
        }
    }

    private void validateOption(Immobile immobile) {
        if (Objects.isNull(immobile.getOption())) {
            throw new IllegalArgumentException("Option must be provided");
        }

        switch (immobile.getOption()) {
            case SALE:
                validateSaleOption(immobile);
                break;
            case RENT:
                validateRentOption(immobile);
                break;
            case SALE_RENT:
                validateSaleRentOption(immobile);
                break;
            default:
                throw new IllegalArgumentException("Invalid option: " + immobile.getOption());
        }
    }

    private void validateSaleOption(Immobile immobile) {
        if (Objects.isNull(immobile.getSalePrice()) || immobile.getSalePrice() <= 0) {
            throw new IllegalArgumentException("Sale price must be provided!");
        }
        if (Objects.nonNull(immobile.getRentPrice()) && immobile.getRentPrice() > 0) {
            throw new IllegalArgumentException("Rent price must be null for SALE option");
        }
    }

    private void validateRentOption(Immobile immobile) {
        if (Objects.isNull(immobile.getRentPrice()) || immobile.getRentPrice() <= 0) {
            throw new IllegalArgumentException("Rent price must be provided!");
        }
        if (Objects.nonNull(immobile.getSalePrice()) && immobile.getSalePrice() > 0) {
            throw new IllegalArgumentException("Sale price must be null for RENT option");
        }
    }

    private void validateSaleRentOption(Immobile immobile) {
        if (Objects.isNull(immobile.getSalePrice()) || immobile.getSalePrice() <= 0) {
            throw new IllegalArgumentException("Sale price must be provided for SALE_RENT option!");
        }
        if (Objects.isNull(immobile.getRentPrice()) || immobile.getRentPrice() <= 0) {
            throw new IllegalArgumentException("Rent price must be provided for SALE_RENT option");
        }
    }

}
