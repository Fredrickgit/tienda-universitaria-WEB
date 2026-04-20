package com.unimag.tiendauniversitaria.mapper;

import com.unimag.tiendauniversitaria.dto.response.OrderItemResponse;
import com.unimag.tiendauniversitaria.dto.response.OrderResponse;
import com.unimag.tiendauniversitaria.dto.response.OrderStatusHistoryResponse;
import com.unimag.tiendauniversitaria.entity.Order;
import com.unimag.tiendauniversitaria.entity.OrderItem;
import com.unimag.tiendauniversitaria.entity.OrderStatusHistory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entidades Order a DTOs de respuesta
 * Proporciona métodos estáticos para mapeo sin necesidad de componentes
 */
public class OrderMapper {

    private OrderMapper() {
        // Clase utilitaria, no instanciable
    }

    /**
     * Convierte una entidad Order a OrderResponse
     * Incluye información del cliente, dirección, ítems e historial
     *
     * @param order la entidad Order a convertir
     * @return OrderResponse con todos los datos mapeados
     */
    public static OrderResponse toResponse(Order order) {
        if (order == null) {
            return null;
        }

        return OrderResponse.builder()
                .id(order.getId())
                .status(order.getStatus())
                .total(order.getTotal())
                .createdAt(order.getCreatedAt())
                .customerId(order.getCustomer() != null ? order.getCustomer().getId() : null)
                .customerName(order.getCustomer() != null ? order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName() : null)
                .addressId(order.getAddress() != null ? order.getAddress().getId() : null)
                .addressStreet(order.getAddress() != null ? order.getAddress().getStreet() : null)
                .addressCity(order.getAddress() != null ? order.getAddress().getCity() : null)
                .items(toItemList(order.getItems()))
                .statusHistory(toStatusHistoryList(order.getStatusHistory()))
                .build();
    }

    /**
     * Convierte una entidad OrderItem a OrderItemResponse
     * Incluye información del producto
     *
     * @param item la entidad OrderItem a convertir
     * @return OrderItemResponse con todos los datos mapeados
     */
    public static OrderItemResponse toItemResponse(OrderItem item) {
        if (item == null) {
            return null;
        }

        return OrderItemResponse.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .subtotal(item.getSubtotal())
                .productId(item.getProduct() != null ? item.getProduct().getId() : null)
                .productSku(item.getProduct() != null ? item.getProduct().getSku() : null)
                .productName(item.getProduct() != null ? item.getProduct().getName() : null)
                .build();
    }

    /**
     * Convierte una lista de OrderItem a lista de OrderItemResponse
     *
     * @param items lista de entidades OrderItem
     * @return lista de OrderItemResponse mapeados
     */
    public static List<OrderItemResponse> toItemList(List<OrderItem> items) {
        if (items == null) {
            return null;
        }

        return items.stream()
                .map(OrderMapper::toItemResponse)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una entidad OrderStatusHistory a OrderStatusHistoryResponse
     *
     * @param history la entidad OrderStatusHistory a convertir
     * @return OrderStatusHistoryResponse con todos los datos mapeados
     */
    public static OrderStatusHistoryResponse toStatusHistoryResponse(OrderStatusHistory history) {
        if (history == null) {
            return null;
        }

        return OrderStatusHistoryResponse.builder()
                .id(history.getId())
                .status(history.getStatus())
                .notes(history.getNotes())
                .changedAt(history.getChangedAt())
                .build();
    }

    /**
     * Convierte una lista de OrderStatusHistory a lista de OrderStatusHistoryResponse
     *
     * @param histories lista de entidades OrderStatusHistory
     * @return lista de OrderStatusHistoryResponse mapeados
     */
    public static List<OrderStatusHistoryResponse> toStatusHistoryList(List<OrderStatusHistory> histories) {
        if (histories == null) {
            return null;
        }

        return histories.stream()
                .map(OrderMapper::toStatusHistoryResponse)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una lista de Order a lista de OrderResponse
     * Útil para operaciones que retornan múltiples pedidos
     *
     * @param orders lista de entidades Order
     * @return lista de OrderResponse mapeados
     */
    public static List<OrderResponse> toResponseList(List<Order> orders) {
        if (orders == null) {
            return null;
        }

        return orders.stream()
                .map(OrderMapper::toResponse)
                .collect(Collectors.toList());
    }
}
