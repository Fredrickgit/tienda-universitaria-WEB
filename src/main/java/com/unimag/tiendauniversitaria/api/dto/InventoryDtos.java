package com.unimag.tiendauniversitaria.api.dto;

import jakarta.validation.constraints.*;

public class InventoryDtos {
    public record InventoryCreateRequest(
            @NotNull Long productId,
            @NotNull Integer quantity
    ) {}

    public record InventoryUpdateRequest(
            @NotNull Integer quantity
    ) {}

    public record InventoryResponse(
            Long id,
            Long productId,
            Integer quantity
    ) {}
}
