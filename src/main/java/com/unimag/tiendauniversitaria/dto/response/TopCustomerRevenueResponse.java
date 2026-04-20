package com.unimag.tiendauniversitaria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO de respuesta para clientes con mayor ingresos (top customers)
 * Contiene información del cliente y el total de ingresos generado
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopCustomerRevenueResponse {
    private Long customerId;
    private String customerName;
    private BigDecimal totalRevenue;
}
