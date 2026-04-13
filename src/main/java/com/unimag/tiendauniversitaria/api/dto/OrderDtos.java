package com.unimag.tiendauniversitaria.api.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public class OrderDtos {
    public record OrderCreateRequest(
            @NotNull Long customerId,
            @NotEmpty List<OrderItemDtos.OrderItemRequest> items
    ) {}

    public record OrderResponse(
            Long id,
            Long customerId,
            List<OrderItemDtos.OrderItemResponse> items,
            BigDecimal total    ) {}
}
