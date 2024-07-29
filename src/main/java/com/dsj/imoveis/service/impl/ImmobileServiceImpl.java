package com.dsj.imoveis.service.impl;

import com.dsj.imoveis.lib.dto.ImmobileDTO;
import com.dsj.imoveis.lib.dto.ImmobileMinDTO;
import com.dsj.imoveis.lib.entities.Immobile;
import com.dsj.imoveis.lib.enums.*;
import com.dsj.imoveis.mapper.ImmobileMapper;
import com.dsj.imoveis.repository.ImmobileRepository;
import com.dsj.imoveis.service.ImmobileService;
import com.dsj.imoveis.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImmobileServiceImpl implements ImmobileService {

    private final ImmobileRepository repository;

    private final ImmobileMapper immobileMapper;

    @Override
    @Transactional
    public ImmobileDTO save(ImmobileDTO dto) {
        Immobile entity = immobileMapper.mapImmobile(dto);

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

    private void validateImmobile(Immobile immobile) {
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

        if (Objects.isNull(immobile.getOption())) {
            throw new IllegalArgumentException("Option must be provided");
        }

        switch (immobile.getOption()) {
            case SALE:
                if (Objects.isNull(immobile.getSalePrice()) || immobile.getSalePrice() <= 0) {
                    throw new IllegalArgumentException("Sale price must be provided!");
                }
                if (Objects.nonNull(immobile.getRentPrice()) && immobile.getRentPrice() > 0) {
                    throw new IllegalArgumentException("Rent price must be null for SALE option");
                }
                break;
            case RENT:
                if (Objects.isNull(immobile.getRentPrice()) || immobile.getRentPrice() <= 0) {
                    throw new IllegalArgumentException("Rent price must be provided!");
                }
                if (Objects.nonNull(immobile.getSalePrice()) && immobile.getSalePrice() > 0) {
                    throw new IllegalArgumentException("Sale price must be null for RENT option");
                }
                break;
            case SALE_RENT:
                if (Objects.isNull(immobile.getSalePrice()) || immobile.getSalePrice() <= 0) {
                    throw new IllegalArgumentException("Sale price must be provided for SALE_RENT option!");
                }
                if (Objects.isNull(immobile.getRentPrice()) || immobile.getRentPrice() <= 0) {
                    throw new IllegalArgumentException("Rent price must be provided for SALE_RENT option");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid option: " + immobile.getOption());
        }
    }

}
