package com.dsj.imoveis.mapper.impl;

import com.dsj.imoveis.lib.dto.AddressDTO;
import com.dsj.imoveis.lib.dto.ImmobileDTO;
import com.dsj.imoveis.lib.dto.ImmobileMinDTO;
import com.dsj.imoveis.lib.entities.Address;
import com.dsj.imoveis.lib.entities.Immobile;
import com.dsj.imoveis.mapper.ImmobileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImmobileMapperImpl implements ImmobileMapper {

    @Override
    public Immobile mapImmobile(ImmobileDTO dto) {

        Address address = mapAddress(dto.address());

        Immobile immobile = Immobile.builder()
                .title(dto.title())
                .totalArea(dto.totalArea())
                .privateArea(dto.privateArea())
                .suites(dto.suites())
                .bedrooms(dto.bedrooms())
                .garage(dto.garage())
                .description(dto.description())
                .iptu(dto.iptu())
                .salePrice(dto.salePrice())
                .rentPrice(dto.rentPrice())
                .subtype(dto.subtype())
                .characteristics(dto.characteristics())
                .imageUrls(dto.imageUrls())
                .category(dto.category())
                .address(address)
                .build();

        address.setImmobile(immobile);

        return immobile;
    }

    @Override
    public ImmobileDTO mapImmobileDTO(Immobile entity) {
        return ImmobileDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .totalArea(entity.getTotalArea())
                .privateArea(entity.getPrivateArea())
                .suites(entity.getSuites())
                .bedrooms(entity.getBedrooms())
                .garage(entity.getGarage())
                .description(entity.getDescription())
                .characteristics(entity.getCharacteristics())
                .iptu(entity.getIptu())
                .salePrice(entity.getSalePrice())
                .rentPrice(entity.getRentPrice())
                .subtype(entity.getSubtype())
                .category(entity.getCategory())
                .imageUrls(entity.getImageUrls())
                .address(mapAddressDTO(entity.getAddress()))
                .build();
    }

    @Override
    public ImmobileMinDTO mapImmobileMinDTO(Immobile entity) {
        return  ImmobileMinDTO.builder().id(entity.getId())
                .title(entity.getTitle())
                .totalArea(entity.getTotalArea())
                .suites(entity.getSuites())
                .bedrooms(entity.getBedrooms())
                .garage(entity.getGarage())
                .salePrice(entity.getSalePrice())
                .rentPrice(entity.getRentPrice())
                .subtype(entity.getSubtype())
                .category(entity.getCategory())
                .address(mapAddressDTO(entity.getAddress()))
                .build();
    }

    private Address mapAddress(AddressDTO dto) {
        return Address.builder()
                .neighborhood(dto.neighborhood())
                .city(dto.city())
                .state(dto.state())
                .zipCode(dto.zipCode())
                .build();
    }

    private AddressDTO mapAddressDTO(Address entity) {
        return AddressDTO.builder()
                .neighborhood(entity.getNeighborhood())
                .city(entity.getCity())
                .state(entity.getState())
                .zipCode(entity.getZipCode())
                .build();
    }
}
