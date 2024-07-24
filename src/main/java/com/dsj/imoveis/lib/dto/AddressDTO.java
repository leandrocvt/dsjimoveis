package com.dsj.imoveis.lib.dto;

import lombok.Builder;

@Builder
public record AddressDTO(
         String neighborhood,
         String city,
         String state,
         String zipCode
) {
}
