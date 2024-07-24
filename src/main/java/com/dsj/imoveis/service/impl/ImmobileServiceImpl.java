package com.dsj.imoveis.service.impl;

import com.dsj.imoveis.lib.dto.ImmobileDTO;
import com.dsj.imoveis.lib.entities.Immobile;
import com.dsj.imoveis.lib.enums.CommercialType;
import com.dsj.imoveis.lib.enums.ResidentialType;
import com.dsj.imoveis.lib.enums.RuralType;
import com.dsj.imoveis.mapper.ImmobileMapper;
import com.dsj.imoveis.repository.ImmobileRepository;
import com.dsj.imoveis.service.ImmobileService;
import com.dsj.imoveis.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

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

    private void validateImmobile(Immobile immobile) {
        if (immobile.getCategory() == null) {
            throw new IllegalArgumentException("Category must be provided");
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
                    throw new IllegalArgumentException("Invalid category");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid subtype for category", e);
        }
    }

}
