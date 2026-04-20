package com.unimag.tiendauniversitaria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO de respuesta para información de inventario
 * Contiene datos de stock disponible y mínimo, con fecha de actualización
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
    private Integer availableStock;
    private Integer minimumStock;
    private LocalDateTime updatedAt;
}
