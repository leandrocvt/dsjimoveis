package com.dsj.imoveis.lib.dto;

import com.dsj.imoveis.lib.enums.ImmobileCategory;
import com.dsj.imoveis.lib.enums.OptionImmobile;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

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
        Boolean highlight,
        ImmobileCategory category,
        OptionImmobile option,

        String firstImageUrl,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        AddressDTO address

) {
}
