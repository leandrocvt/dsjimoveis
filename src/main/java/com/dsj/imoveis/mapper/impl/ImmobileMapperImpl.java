package com.dsj.imoveis.mapper.impl;

import com.dsj.imoveis.lib.dto.AddressDTO;
import com.dsj.imoveis.lib.dto.ImmobileDTO;
import com.dsj.imoveis.lib.dto.ImmobileMinDTO;
import com.dsj.imoveis.lib.entities.Address;
import com.dsj.imoveis.lib.entities.Immobile;
import com.dsj.imoveis.mapper.ImmobileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

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
                .highlight(dto.highlight())
                .subtype(dto.subtype())
                .characteristics(dto.characteristics())
                .imageUrls(dto.imageUrls())
                .category(dto.category())
                .address(address)
                .option(dto.option())
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
                .highlight(entity.getHighlight())
                .subtype(entity.getSubtype())
                .category(entity.getCategory())
                .imageUrls(entity.getImageUrls())
                .address(mapAddressDTO(entity.getAddress()))
                .option(entity.getOption())
                .build();
    }

    @Override
    public ImmobileMinDTO mapImmobileMinDTO(Immobile entity) {
        List<String> imageUrls = entity.getImageUrls();
        String firstImageUrl = (imageUrls != null && !imageUrls.isEmpty()) ? imageUrls.get(0) : null;

        return  ImmobileMinDTO.builder().id(entity.getId())
                .title(entity.getTitle())
                .firstImageUrl(firstImageUrl)
                .totalArea(entity.getTotalArea())
                .suites(entity.getSuites())
                .bedrooms(entity.getBedrooms())
                .garage(entity.getGarage())
                .salePrice(entity.getSalePrice())
                .rentPrice(entity.getRentPrice())
                .highlight(entity.getHighlight())
                .subtype(entity.getSubtype())
                .category(entity.getCategory())
                .address(mapAddressDTO(entity.getAddress()))
                .option(entity.getOption())
                .build();
    }

    @Override
    public void updateImmobileFromDTO(ImmobileDTO dto, Immobile entity) {

        entity.setTitle(dto.title());
        entity.setTotalArea(dto.totalArea());
        entity.setPrivateArea(dto.privateArea());
        entity.setSuites(dto.suites());
        entity.setBedrooms(dto.bedrooms());
        entity.setGarage(dto.garage());
        entity.setDescription(dto.description());
        entity.setIptu(dto.iptu());
        entity.setSalePrice(dto.salePrice());
        entity.setRentPrice(dto.rentPrice());
        entity.setHighlight(dto.highlight());
        entity.setSubtype(dto.subtype());
        entity.setCharacteristics(dto.characteristics());
        entity.setImageUrls(dto.imageUrls());
        entity.setCategory(dto.category());
        entity.setOption(dto.option());

        if (Objects.isNull(entity.getAddress())) {
            entity.setAddress(new Address());
        }

        Address address = entity.getAddress();
        AddressDTO addressDTO = dto.address();

        if (Objects.nonNull(addressDTO)) {
            address.setNeighborhood(addressDTO.neighborhood());
            address.setCity(addressDTO.city());
            address.setState(addressDTO.state());
            address.setZipCode(addressDTO.zipCode());
            address.setImmobile(entity);
        }
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
