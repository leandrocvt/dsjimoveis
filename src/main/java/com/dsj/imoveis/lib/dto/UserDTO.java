package com.dsj.imoveis.lib.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UserDTO(
        Long id,
        String name,
        String email,
        List<String> roles
) {
}
