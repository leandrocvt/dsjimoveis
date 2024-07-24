package com.dsj.imoveis.lib.dto;

import com.dsj.imoveis.lib.entities.Immobile;
import com.dsj.imoveis.lib.enums.ImmobileCategory;
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
         String subtype,
         ImmobileCategory category,
         List<String> imageUrls,
         AddressDTO address
) {
}
