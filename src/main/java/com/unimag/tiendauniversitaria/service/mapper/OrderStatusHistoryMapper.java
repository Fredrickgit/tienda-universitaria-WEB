package com.unimag.tiendauniversitaria.service.mapper;

import com.unimag.tiendauniversitaria.api.dto.OrderStatushistoryDtos;
import com.unimag.tiendauniversitaria.entity.OrderStatusHistory;

public class OrderStatusHistoryMapper {

    public static OrderStatushistoryDtos.OrderStatusHistoryResponse toResponse(OrderStatusHistory s) {
        return new OrderStatushistoryDtos.OrderStatusHistoryResponse(
                s.getId(),
                s.getOrder().getId(),
                s.getStatus().name(),
                s.getChangedAt().toString()
        );
    }
}