package com.unimag.tiendauniversitaria.dto.response;

import com.unimag.tiendauniversitaria.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO de respuesta para el historial de cambios de estado de un pedido
 * Representa un registro del estado en un momento específico
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusHistoryResponse {
    private Long id;
    private OrderStatus status;
    private String notes;
    private LocalDateTime changedAt;
}
