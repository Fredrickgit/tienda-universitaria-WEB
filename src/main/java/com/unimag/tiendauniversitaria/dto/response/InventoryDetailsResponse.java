package com.unimag.tiendauniversitaria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO de respuesta extendido para inventario
 * Incluye detalles del producto asociado además de los datos de stock
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDetailsResponse {
    private Long id;
    private Integer availableStock;
    private Integer minimumStock;
    private LocalDateTime updatedAt;
    private Long productId;
    private String productSku;
    private String productName;
    private Boolean isLowStock;
}
