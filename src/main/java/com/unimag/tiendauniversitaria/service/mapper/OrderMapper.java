package com.unimag.tiendauniversitaria.service.mapper;

import com.unimag.tiendauniversitaria.api.dto.OrderDtos;
import com.unimag.tiendauniversitaria.api.dto.OrderItemDtos;
import com.unimag.tiendauniversitaria.entity.Order;

import java.util.List;

public class OrderMapper {

    public static OrderDtos.OrderResponse toResponse(Order o) {

        List<OrderItemDtos.OrderItemResponse> items = o.getItems().stream()
                .map(OrderItemMapper::toResponse)
                .toList();

        return new OrderDtos.OrderResponse(
                o.getId(),
                o.getCustomer().getId(),
                items,
                o.getTotal()
        );
    }
}