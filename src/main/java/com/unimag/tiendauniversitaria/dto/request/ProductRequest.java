package com.unimag.tiendauniversitaria.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO consolidado para operaciones de productos
 * Maneja: crear, actualizar y crear con inventario
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "SKU is required")
    @Size(max = 50, message = "SKU must not exceed 50 characters")
    private String sku;

    @NotBlank(message = "Product name is required")
    @Size(max = 150, message = "Product name must not exceed 150 characters")
    private String name;

    @Size(max = 500, message = "Product description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    // Para crear producto con inventario
    @PositiveOrZero(message = "Initial stock must be greater than or equal to zero")
    private Integer initialStock;

    @PositiveOrZero(message = "Minimum stock must be greater than or equal to zero")
    private Integer minimumStock;

    /**
     * Tipo de operación: CREATE, UPDATE, CREATE_WITH_INVENTORY
     */
    @NotNull(message = "Operation type is required")
    private ProductOperationType operationType;

    public enum ProductOperationType {
        CREATE,
        UPDATE,
        CREATE_WITH_INVENTORY
    }
}
