package com.dsj.imoveis.lib.dto;

import com.dsj.imoveis.lib.entities.Immobile;
import com.dsj.imoveis.lib.enums.ImmobileCategory;
import com.dsj.imoveis.lib.enums.OptionImmobile;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
public record ImmobileMinDTO(
         Long id,
         String title,
         Double totalArea,
         Integer suites,
         Integer bedrooms,
         Integer garage,
         Double salePrice,
         Double rentPrice,
         String subtype,
         ImmobileCategory category,
         @JsonInclude(JsonInclude.Include.NON_NULL)
         AddressDTO address,
         OptionImmobile option
) {
}
