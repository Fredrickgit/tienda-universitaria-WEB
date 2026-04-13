package com.unimag.tiendauniversitaria.service.mapper;

import com.unimag.tiendauniversitaria.api.dto.OrderItemDtos;
import com.unimag.tiendauniversitaria.entity.Order;
import com.unimag.tiendauniversitaria.entity.OrderItem;
import com.unimag.tiendauniversitaria.entity.Product;

import java.math.BigDecimal;

public class OrderItemMapper {

    public static OrderItem toEntity(OrderItemDtos.OrderItemRequest req, Order order, Product product) {
        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(req.quantity());
        return item;
    }

    public static OrderItemDtos.OrderItemResponse toResponse(OrderItem i) {
        BigDecimal subtotal = i.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(i.getQuantity()));
        return new OrderItemDtos.OrderItemResponse(
                i.getProduct().getId(),
                i.getProduct().getName(),
                i.getQuantity(),
                i.getProduct().getPrice(),
                subtotal
                );
    }
}