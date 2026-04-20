package com.unimag.tiendauniversitaria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de respuesta para producto
 * Contiene todos los datos del producto incluyendo categoría e inventario
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;
    private Long categoryId;
    private String categoryName;
    private InventoryResponse inventory;
    private LocalDateTime createdAt;
}
