package com.unimag.tiendauniversitaria.controller;

import com.unimag.tiendauniversitaria.dto.ApiResponse;
import com.unimag.tiendauniversitaria.dto.request.OrderCreateRequest;
import com.unimag.tiendauniversitaria.dto.response.OrderResponse;
import com.unimag.tiendauniversitaria.dto.response.OrderStatusHistoryResponse;
import com.unimag.tiendauniversitaria.entity.Order;
import com.unimag.tiendauniversitaria.entity.OrderStatusHistory;
import com.unimag.tiendauniversitaria.mapper.OrderMapper;
import com.unimag.tiendauniversitaria.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controlador REST para la gestión de pedidos
 * Expone los servicios de OrderService a través de endpoints HTTP
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * POST /api/orders
     * Crea un nuevo pedido
     *
     * @param request DTO con customerId, addressId e items
     * @return ResponseEntity con OrderResponse y status 201 CREATED
     */
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @Valid @RequestBody OrderCreateRequest request) {
        
        // Convertir DTO request a interno de OrderService
        OrderService.CreateOrderRequest serviceRequest = new OrderService.CreateOrderRequest(
                request.getCustomerId(),
                request.getAddressId(),
                request.getItems().stream()
                        .map(item -> new OrderService.CreateOrderItemRequest(item.getProductId(), item.getQuantity()))
                        .toList()
        );
        
        Order order = orderService.createOrder(serviceRequest);
        OrderResponse response = OrderMapper.toResponse(order);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ofSuccess(response, "Order created successfully"));
    }

    /**
     * GET /api/orders/{id}
     * Obtiene un pedido por ID
     *
     * @param id ID del pedido
     * @return ResponseEntity con OrderResponse y status 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(@PathVariable Long id) {
        Order order = orderService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order with ID " + id + " not found"));
        
        OrderResponse response = OrderMapper.toResponse(order);
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Order found"));
    }

    /**
     * GET /api/orders/customer/{customerId}
     * Obtiene todos los pedidos de un cliente
     *
     * @param customerId ID del cliente
     * @return ResponseEntity con lista de OrderResponse y status 200 OK
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByCustomerId(
            @PathVariable Long customerId) {
        
        List<Order> orders = orderService.findByCustomerId(customerId);
        List<OrderResponse> responses = OrderMapper.toResponseList(orders);
        
        return ResponseEntity.ok(ApiResponse.ofSuccess(responses, "Orders retrieved successfully"));
    }

    /**
     * POST /api/orders/{id}/process-payment
     * Procesa el pago de un pedido (CREATED → PAID)
     * Valida stock y descuenta inventario
     *
     * @param id ID del pedido
     * @return ResponseEntity con OrderResponse actualizado
     */
    @PostMapping("/{id}/process-payment")
    public ResponseEntity<ApiResponse<OrderResponse>> processPayment(@PathVariable Long id) {
        Order order = orderService.processPayment(id);
        OrderResponse response = OrderMapper.toResponse(order);
        
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Payment processed successfully"));
    }

    /**
     * POST /api/orders/{id}/ship
     * Despacha un pedido (PAID → SHIPPED)
     *
     * @param id ID del pedido
     * @return ResponseEntity con OrderResponse actualizado
     */
    @PostMapping("/{id}/ship")
    public ResponseEntity<ApiResponse<OrderResponse>> shipOrder(@PathVariable Long id) {
        Order order = orderService.shipOrder(id);
        OrderResponse response = OrderMapper.toResponse(order);
        
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Order shipped successfully"));
    }

    /**
     * POST /api/orders/{id}/deliver
     * Marca un pedido como entregado (SHIPPED → DELIVERED)
     *
     * @param id ID del pedido
     * @return ResponseEntity con OrderResponse actualizado
     */
    @PostMapping("/{id}/deliver")
    public ResponseEntity<ApiResponse<OrderResponse>> deliverOrder(@PathVariable Long id) {
        Order order = orderService.deliverOrder(id);
        OrderResponse response = OrderMapper.toResponse(order);
        
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Order delivered successfully"));
    }

    /**
     * POST /api/orders/{id}/cancel
     * Cancela un pedido según reglas de estado
     * Si está PAID, revierte el stock
     *
     * @param id ID del pedido
     * @return ResponseEntity con OrderResponse actualizado
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<OrderResponse>> cancelOrder(@PathVariable Long id) {
        Order order = orderService.cancelOrder(id);
        OrderResponse response = OrderMapper.toResponse(order);
        
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Order cancelled successfully"));
    }

    /**
     * GET /api/orders/{id}/history
     * Obtiene el historial de cambios de estado de un pedido
     *
     * @param id ID del pedido
     * @return ResponseEntity con lista de OrderStatusHistoryResponse
     */
    @GetMapping("/{id}/history")
    public ResponseEntity<ApiResponse<List<OrderStatusHistoryResponse>>> getOrderHistory(
            @PathVariable Long id) {
        
        List<OrderStatusHistory> histories = orderService.getOrderHistory(id);
        List<OrderStatusHistoryResponse> responses = OrderMapper.toStatusHistoryList(histories);
        
        return ResponseEntity.ok(ApiResponse.ofSuccess(responses, "Order history retrieved successfully"));
    }

    /**
     * GET /api/orders/{id}/total
     * Obtiene el total de un pedido
     *
     * @param id ID del pedido
     * @return ResponseEntity con el total del pedido
     */
    @GetMapping("/{id}/total")
    public ResponseEntity<ApiResponse<BigDecimal>> getOrderTotal(@PathVariable Long id) {
        BigDecimal total = orderService.calculateOrderTotal(id);
        
        return ResponseEntity.ok(ApiResponse.ofSuccess(total, "Order total retrieved successfully"));
    }
}
