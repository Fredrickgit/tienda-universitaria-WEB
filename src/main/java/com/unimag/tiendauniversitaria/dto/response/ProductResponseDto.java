package com.unimag.tiendauniversitaria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO consolidado de respuesta para operaciones de productos
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    
    private Long productId;
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private String categoryName;
    private Boolean active;
    
    // Información de inventario (cuando aplique)
    private Integer availableStock;
    private Integer minimumStock;
    
    private LocalDateTime createdAt;
}
