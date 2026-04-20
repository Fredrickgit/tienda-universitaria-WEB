package com.unimag.tiendauniversitaria.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de solicitud para actualizar el stock mínimo de un inventario
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdateMinimumStockRequest {

    @NotNull(message = "New minimum stock is required")
    @PositiveOrZero(message = "New minimum stock must be greater than or equal to zero")
    private Integer newMinimumStock;
}
