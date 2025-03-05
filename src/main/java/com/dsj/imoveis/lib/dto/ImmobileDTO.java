package com.dsj.imoveis.lib.dto;

import com.dsj.imoveis.lib.enums.ImmobileCategory;
import com.dsj.imoveis.lib.enums.OptionImmobile;
import lombok.Builder;

import java.util.List;

@Builder
public record ImmobileDTO(
         Long id,
         String title,
         Double totalArea,
         Double privateArea,
         Integer suites,
         Integer bedrooms,
         Integer garage,
         String description,
         List<String>characteristics,
         Double iptu,
         Double salePrice,
         Double rentPrice,
         Boolean highlight,
         String subtype,
         ImmobileCategory category,
         OptionImmobile option,
         List<String> imageUrls,
         AddressDTO address
) {
    public ImmobileDTOBuilder toBuilder() {
        return builder()
                .id(id)
                .title(title)
                .totalArea(totalArea)
                .privateArea(privateArea)
                .suites(suites)
                .bedrooms(bedrooms)
                .garage(garage)
                .description(description)
                .characteristics(characteristics)
                .iptu(iptu)
                .salePrice(salePrice)
                .rentPrice(rentPrice)
                .highlight(highlight)
                .subtype(subtype)
                .category(category)
                .option(option)
                .imageUrls(imageUrls)
                .address(address);
    }
}
