package com.unimag.tiendauniversitaria.api.dto;

import jakarta.validation.constraints.*;

public class CustomerDtos {
    public record CustomerCreateRequest(
            @NotBlank String name,
            @Email String email
    ) {}

    public record CustomerUpdateRequest(
            @NotBlank String name,
            @Email String email
    ) {}

    public record CustomerResponse(
            Long id,
            String name,
            String email
    ) {}
}
