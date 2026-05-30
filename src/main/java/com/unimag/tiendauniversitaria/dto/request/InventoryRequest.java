package com.unimag.tiendauniversitaria.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO consolidado para todas las operaciones de inventario
 * Maneja: crear, incrementar, decrementar y actualizar stock
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequest {

    // Para crear inventario inicial
    private Long productId;

    @PositiveOrZero(message = "Initial stock must be greater than or equal to zero")
    private Integer initialStock;

    @PositiveOrZero(message = "Minimum stock must be greater than or equal to zero")
    private Integer minimumStock;

    // Para incrementar/decrementar/actualizar stock
    @Positive(message = "Quantity must be greater than zero")
    private Integer quantity;

    @PositiveOrZero(message = "Available stock must be greater than or equal to zero")
    private Integer availableStock;

    /**
     * Tipo de operación: CREATE, INCREMENT, DECREMENT, UPDATE_AVAILABLE, UPDATE_MINIMUM
     */
    private InventoryOperationType operationType;

    public enum InventoryOperationType {
        CREATE,
        INCREMENT,
        DECREMENT,
        UPDATE_AVAILABLE,
        UPDATE_MINIMUM
    }
}
