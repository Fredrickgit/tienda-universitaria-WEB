package com.unimag.tiendauniversitaria.dto.response;

import com.unimag.tiendauniversitaria.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de respuesta completo para un pedido
 * Incluye ítems, información del cliente y dirección
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private OrderStatus status;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private Long customerId;
    private String customerName;
    private Long addressId;
    private String addressStreet;
    private String addressCity;
    private List<OrderItemResponse> items;
    private List<OrderStatusHistoryResponse> statusHistory;
}
