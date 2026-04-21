package com.unimag.tiendauniversitaria.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO consolidado para operaciones de órdenes
 * Maneja: crear órdenes con items y filtrar órdenes
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    // Para crear orden
    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotEmpty(message = "Order items cannot be empty")
    @Valid
    private List<OrderItemRequest> items;

    // Para filtrar órdenes
    private Long orderId;
    private Long customerIdFilter;
    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    /**
     * Tipo de operación: CREATE, FILTER
     */
    @NotNull(message = "Operation type is required")
    private OrderOperationType operationType;

    public enum OrderOperationType {
        CREATE,
        FILTER
    }

    /**
     * DTO anidado para items de orden
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemRequest {

        @NotNull(message = "Product ID is required")
        private Long productId;

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be greater than zero")
        private Integer quantity;
    }
}
