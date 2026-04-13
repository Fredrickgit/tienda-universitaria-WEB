package com.unimag.tiendauniversitaria.api;

import com.unimag.tiendauniversitaria.api.dto.OrderStatushistoryDtos;
import com.unimag.tiendauniversitaria.service.OrderStatusHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-status-history")
@RequiredArgsConstructor
@Validated
public class OrderStatusHistoryController {

    private final OrderStatusHistoryService service;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderStatushistoryDtos.OrderStatusHistoryResponse>> getByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(service.getByOrder(orderId));
    }
}