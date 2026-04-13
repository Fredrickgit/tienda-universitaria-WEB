package com.unimag.tiendauniversitaria.api.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ProductDtos {
    public record ProductCreateRequest(
            @NotBlank String name,
            @NotNull BigDecimal price,
            @NotNull Long categoryId
    ) {}

    public record ProductUpdateRequest(
            @NotBlank String name,
            @NotNull BigDecimal price,
            @NotNull Long categoryId
    ) {}

    public record ProductResponse(
            Long id,
            String name,
            BigDecimal price,
            String categoryName
    ) {}
}
