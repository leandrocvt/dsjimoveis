package com.dsj.imoveis.lib.dto;

public record ContactRequest(

        String name,
        String email,
        String phoneNumber,
        Long immobileId
) {
}
