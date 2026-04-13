package com.unimag.tiendauniversitaria.api.dto;

import jakarta.validation.constraints.*;

public class OrderItemDtos {
    // OrderItemRequest.java
    public record OrderItemRequest(
            @NotNull Long productId,
            @NotNull Integer quantity
    ) {}
    // OrderItemResponse.java
    public record OrderItemResponse(
            Long productId,
            String productName,
            Integer quantity,
            Double price,
            Double subtotal
    ) {}
}
