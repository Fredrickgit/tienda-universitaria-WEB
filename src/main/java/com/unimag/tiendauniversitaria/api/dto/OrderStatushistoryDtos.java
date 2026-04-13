package com.unimag.tiendauniversitaria.api.dto;

public class OrderStatushistoryDtos {
    public record OrderStatusHistoryResponse(
            Long id,
            Long orderId,
            String status,
            String changedAt
    ) {}
}
