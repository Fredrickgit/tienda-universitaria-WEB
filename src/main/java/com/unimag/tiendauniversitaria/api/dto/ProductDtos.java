package com.unimag.tiendauniversitaria.api.dto;

import jakarta.validation.constraints.*;

public class ProductDtos {
    public record ProductCreateRequest(
            @NotBlank String name,
            @NotNull Double price,
            @NotNull Long categoryId
    ) {}

    public record ProductUpdateRequest(
            @NotBlank String name,
            @NotNull Double price,
            @NotNull Long categoryId
    ) {}

    public record ProductResponse(
            Long id,
            String name,
            Double price,
            String categoryName
    ) {}
}
