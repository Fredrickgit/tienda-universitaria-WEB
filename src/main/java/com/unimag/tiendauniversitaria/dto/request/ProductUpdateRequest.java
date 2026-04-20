package com.unimag.tiendauniversitaria.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO de solicitud para actualizar un producto
 * Todos los campos son opcionales para permitir actualizaciones parciales
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {

    @Size(max = 150, message = "Product name must not exceed 150 characters")
    private String name;

    @Size(max = 500, message = "Product description must not exceed 500 characters")
    private String description;

    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    private Long categoryId;
}
