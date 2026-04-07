package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.entity.Inventory;
import com.unimag.tiendauniversitaria.entity.Order;
import com.unimag.tiendauniversitaria.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {

    List<Inventory> getLowStockProducts();

    List<Inventory> getProductsWithInsufficientStock();

    List<Order> getOrdersByFilters(Long customerId,
                                   com.unimag.tiendauniversitaria.enums.OrderStatus status,
                                   LocalDateTime startDate,
                                   LocalDateTime endDate,
                                   BigDecimal minTotal,
                                   BigDecimal maxTotal);

    List<Product> getTopSellingProductsByPeriod(LocalDateTime startDate, LocalDateTime endDate);

    List<Object[]> getMonthlyRevenue(LocalDateTime startDate, LocalDateTime endDate);

    List<Object[]> getTopCustomersByRevenue(LocalDateTime startDate, LocalDateTime endDate);
}