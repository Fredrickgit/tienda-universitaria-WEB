package com.unimag.tiendauniversitaria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO consolidado de respuesta para operaciones de órdenes
 * Unifica OrderResponse, OrderItemResponse y OrderStatusHistoryResponse
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    
    private Long orderId;
    private Long customerId;
    private String customerName;
    private Long addressId;
    private String addressStreet;
    private String addressCity;
    private String addressDepartment;
    private String addressPostalCode;
    private String status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Detalles de items
    private List<OrderItemDto> items;
    
    // Historial de estado
    private List<OrderStatusHistoryDto> statusHistory;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemDto {
        private Long itemId;
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal subtotal;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderStatusHistoryDto {
        private Long historyId;
        private String status;
        private LocalDateTime changedAt;
        private String changedBy;
    }
}
