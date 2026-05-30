package com.unimag.tiendauniversitaria.controller;

import com.unimag.tiendauniversitaria.dto.ApiResponse;
import com.unimag.tiendauniversitaria.dto.request.OrderRequest;
import com.unimag.tiendauniversitaria.dto.response.OrderResponseDto;
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
     * @param request DTO con customerId e items
     * @return ResponseEntity con OrderResponseDto y status 201 CREATED
     */
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(
            @Valid @RequestBody OrderRequest request) {
        
        // Convertir DTO request a interno de OrderService
        OrderService.CreateOrderRequest serviceRequest = new OrderService.CreateOrderRequest(
                request.getCustomerId(),
                request.getAddressId(),
                request.getItems().stream()
                        .map(item -> new OrderService.CreateOrderItemRequest(item.getProductId(), item.getQuantity()))
                        .toList()
        );
        
        Order order = orderService.createOrder(serviceRequest);
        OrderResponseDto response = OrderMapper.toResponse(order);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ofSuccess(response, "Order created successfully"));
    }

    /**
     * GET /api/orders/{id}
     * Obtiene un pedido por ID
     *
     * @param id ID del pedido
     * @return ResponseEntity con OrderResponseDto y status 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> getOrderById(@PathVariable Long id) {
        Order order = orderService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order with ID " + id + " not found"));
        
        OrderResponseDto response = OrderMapper.toResponse(order);
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Order found"));
    }

    /**
     * GET /api/orders
     * Obtiene todos los pedidos para administracion.
     *
     * @return ResponseEntity con lista de OrderResponseDto y status 200 OK
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getAllOrders() {
        List<Order> orders = orderService.findAll();
        List<OrderResponseDto> responses = OrderMapper.toResponseList(orders);

        return ResponseEntity.ok(ApiResponse.ofSuccess(responses, "Orders retrieved successfully"));
    }

    /**
     * GET /api/orders/customer/{customerId}
     * Obtiene todos los pedidos de un cliente
     *
     * @param customerId ID del cliente
     * @return ResponseEntity con lista de OrderResponseDto y status 200 OK
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getOrdersByCustomerId(
            @PathVariable Long customerId) {
        
        List<Order> orders = orderService.findByCustomerId(customerId);
        List<OrderResponseDto> responses = OrderMapper.toResponseList(orders);
        
        return ResponseEntity.ok(ApiResponse.ofSuccess(responses, "Orders retrieved successfully"));
    }

    /**
     * POST /api/orders/{id}/process-payment
     * Procesa el pago de un pedido (CREATED → PAID)
     * Valida stock y descuenta inventario
     *
     * @param id ID del pedido
     * @return ResponseEntity con OrderResponseDto actualizado
     */
    @PostMapping("/{id}/process-payment")
    public ResponseEntity<ApiResponse<OrderResponseDto>> processPayment(@PathVariable Long id) {
        Order order = orderService.processPayment(id);
        OrderResponseDto response = OrderMapper.toResponse(order);
        
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Payment processed successfully"));
    }

    /**
     * POST /api/orders/{id}/ship
     * Despacha un pedido (PAID → SHIPPED)
     *
     * @param id ID del pedido
     * @return ResponseEntity con OrderResponseDto actualizado
     */
    @PostMapping("/{id}/ship")
    public ResponseEntity<ApiResponse<OrderResponseDto>> shipOrder(@PathVariable Long id) {
        Order order = orderService.shipOrder(id);
        OrderResponseDto response = OrderMapper.toResponse(order);
        
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Order shipped successfully"));
    }

    /**
     * POST /api/orders/{id}/deliver
     * Marca un pedido como entregado (SHIPPED → DELIVERED)
     *
     * @param id ID del pedido
     * @return ResponseEntity con OrderResponseDto actualizado
     */
    @PostMapping("/{id}/deliver")
    public ResponseEntity<ApiResponse<OrderResponseDto>> deliverOrder(@PathVariable Long id) {
        Order order = orderService.deliverOrder(id);
        OrderResponseDto response = OrderMapper.toResponse(order);
        
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Order delivered successfully"));
    }

    /**
     * POST /api/orders/{id}/cancel
     * Cancela un pedido según reglas de estado
     * Si está PAID, revierte el stock
     *
     * @param id ID del pedido
     * @return ResponseEntity con OrderResponseDto actualizado
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<OrderResponseDto>> cancelOrder(@PathVariable Long id) {
        Order order = orderService.cancelOrder(id);
        OrderResponseDto response = OrderMapper.toResponse(order);
        
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Order cancelled successfully"));
    }

    /**
     * GET /api/orders/{id}/history
     * Obtiene el historial de cambios de estado de un pedido
     *
     * @param id ID del pedido
     * @return ResponseEntity con lista de OrderStatusHistoryDto
     */
    @GetMapping("/{id}/history")
    public ResponseEntity<ApiResponse<List<OrderResponseDto.OrderStatusHistoryDto>>> getOrderHistory(
            @PathVariable Long id) {
        
        List<OrderStatusHistory> histories = orderService.getOrderHistory(id);
        List<OrderResponseDto.OrderStatusHistoryDto> responses = OrderMapper.toStatusHistoryList(histories);
        
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
