package com.unimag.tiendauniversitaria.api.dto;

import jakarta.validation.constraints.*;

public class CategoryDtos {
    public record CategoryCreateRequest(
            @NotBlank String name
    ) {}

    public record CategoryUpdateRequest(
            @NotBlank String name
    ) {}

    public record CategoryResponse(
            Long id,
            String name
    ) {}
}
