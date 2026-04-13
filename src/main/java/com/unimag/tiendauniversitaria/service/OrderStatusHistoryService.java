package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.api.dto.OrderStatushistoryDtos;

import java.util.List;

public interface OrderStatusHistoryService {
    List<OrderStatushistoryDtos.OrderStatusHistoryResponse> getByOrder(Long orderId);
}