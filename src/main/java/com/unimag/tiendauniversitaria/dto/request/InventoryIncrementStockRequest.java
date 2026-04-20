package com.unimag.tiendauniversitaria.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de solicitud para incrementar el stock disponible
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryIncrementStockRequest {

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than zero")
    private Integer quantity;
}
