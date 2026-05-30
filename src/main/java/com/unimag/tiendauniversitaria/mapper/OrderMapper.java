package com.unimag.tiendauniversitaria.mapper;

import com.unimag.tiendauniversitaria.dto.response.OrderResponseDto;
import com.unimag.tiendauniversitaria.entity.Order;
import com.unimag.tiendauniversitaria.entity.OrderItem;
import com.unimag.tiendauniversitaria.entity.OrderStatusHistory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entidades Order a DTOs consolidados de respuesta
 * Proporciona métodos estáticos para mapeo sin necesidad de componentes
 */
public class OrderMapper {

    private OrderMapper() {
        // Clase utilitaria, no instanciable
    }

    /**
     * Convierte una entidad Order a OrderResponseDto
     * Incluye información del cliente, dirección, ítems e historial
     *
     * @param order la entidad Order a convertir
     * @return OrderResponseDto con todos los datos mapeados
     */
    public static OrderResponseDto toResponse(Order order) {
        if (order == null) {
            return null;
        }

        return OrderResponseDto.builder()
                .orderId(order.getId())
                .status(order.getStatus().name())
                .totalAmount(order.getTotal())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .customerId(order.getCustomer() != null ? order.getCustomer().getId() : null)
                .customerName(order.getCustomer() != null ? order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName() : null)
                .addressId(order.getAddress() != null ? order.getAddress().getId() : null)
                .addressStreet(order.getAddress() != null ? order.getAddress().getStreet() : null)
                .addressCity(order.getAddress() != null ? order.getAddress().getCity() : null)
                .addressDepartment(order.getAddress() != null ? order.getAddress().getDepartment() : null)
                .addressPostalCode(order.getAddress() != null ? order.getAddress().getPostalCode() : null)
                .items(toItemList(order.getItems()))
                .statusHistory(toStatusHistoryList(order.getStatusHistory()))
                .build();
    }

    /**
     * Convierte una entidad OrderItem a OrderItemDto
     * Incluye información del producto
     *
     * @param item la entidad OrderItem a convertir
     * @return OrderItemDto con todos los datos mapeados
     */
    public static OrderResponseDto.OrderItemDto toItemResponse(OrderItem item) {
        if (item == null) {
            return null;
        }

        return OrderResponseDto.OrderItemDto.builder()
                .itemId(item.getId())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .subtotal(item.getSubtotal())
                .productId(item.getProduct() != null ? item.getProduct().getId() : null)
                .productName(item.getProduct() != null ? item.getProduct().getName() : null)
                .build();
    }

    /**
     * Convierte una lista de OrderItem a lista de OrderItemDto
     *
     * @param items lista de entidades OrderItem
     * @return lista de OrderItemDto mapeados
     */
    public static List<OrderResponseDto.OrderItemDto> toItemList(List<OrderItem> items) {
        if (items == null) {
            return null;
        }

        return items.stream()
                .map(OrderMapper::toItemResponse)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una entidad OrderStatusHistory a OrderStatusHistoryDto
     *
     * @param history la entidad OrderStatusHistory a convertir
     * @return OrderStatusHistoryDto con todos los datos mapeados
     */
    public static OrderResponseDto.OrderStatusHistoryDto toStatusHistoryResponse(OrderStatusHistory history) {
        if (history == null) {
            return null;
        }

        return OrderResponseDto.OrderStatusHistoryDto.builder()
                .historyId(history.getId())
                .status(history.getStatus().name())
                .changedAt(history.getChangedAt())
                .build();
    }

    /**
     * Convierte una lista de OrderStatusHistory a lista de OrderStatusHistoryDto
     *
     * @param histories lista de entidades OrderStatusHistory
     * @return lista de OrderStatusHistoryDto mapeados
     */
    public static List<OrderResponseDto.OrderStatusHistoryDto> toStatusHistoryList(List<OrderStatusHistory> histories) {
        if (histories == null) {
            return null;
        }

        return histories.stream()
                .map(OrderMapper::toStatusHistoryResponse)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una lista de Order a lista de OrderResponseDto
     * Útil para operaciones que retornan múltiples pedidos
     *
     * @param orders lista de entidades Order
     * @return lista de OrderResponseDto mapeados
     */
    public static List<OrderResponseDto> toResponseList(List<Order> orders) {
        if (orders == null) {
            return null;
        }

        return orders.stream()
                .map(OrderMapper::toResponse)
                .collect(Collectors.toList());
    }
}
