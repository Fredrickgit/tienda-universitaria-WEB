package com.unimag.tiendauniversitaria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO consolidado de respuesta para operaciones de inventario
 * Unifica InventoryResponse e InventoryDetailsResponse
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponseDto {
    
    private Long inventoryId;
    private Long productId;
    private String productName;
    private Integer availableStock;
    private Integer minimumStock;
    private LocalDateTime updatedAt;
}
