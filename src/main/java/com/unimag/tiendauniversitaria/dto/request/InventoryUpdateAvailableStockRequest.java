package com.unimag.tiendauniversitaria.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de solicitud para actualizar el stock disponible de un inventario
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdateAvailableStockRequest {

    @NotNull(message = "New stock is required")
    @PositiveOrZero(message = "New stock must be greater than or equal to zero")
    private Integer newStock;
}
