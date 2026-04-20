package com.unimag.tiendauniversitaria.dto.request;

import com.unimag.tiendauniversitaria.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de solicitud para filtrar pedidos en reportes
 * Todos los parámetros son opcionales
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderFilterRequest {
    private Long customerId;
    private OrderStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal minTotal;
    private BigDecimal maxTotal;
}
