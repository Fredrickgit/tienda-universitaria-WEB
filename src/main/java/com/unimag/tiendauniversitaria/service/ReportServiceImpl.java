package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.entity.Inventory;
import com.unimag.tiendauniversitaria.entity.Order;
import com.unimag.tiendauniversitaria.entity.Product;
import com.unimag.tiendauniversitaria.enums.OrderStatus;
import com.unimag.tiendauniversitaria.repository.InventoryRepository;
import com.unimag.tiendauniversitaria.repository.OrderRepository;
import com.unimag.tiendauniversitaria.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final InventoryRepository inventoryRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Inventory> getLowStockProducts() {
        return inventoryRepository.findLowStockInventories();
    }

    @Override
    public List<Inventory> getProductsWithInsufficientStock() {
        return inventoryRepository.findProductsWithInsufficientStock();
    }

    @Override
    public List<Order> getOrdersByFilters(Long customerId,
                                          OrderStatus status,
                                          LocalDateTime startDate,
                                          LocalDateTime endDate,
                                          BigDecimal minTotal,
                                          BigDecimal maxTotal) {
        return orderRepository.findOrdersByFilters(customerId, status, startDate, endDate, minTotal, maxTotal);
    }

    @Override
    public List<Product> getTopSellingProductsByPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return productRepository.findTopSellingProductsByPeriod(startDate, endDate);
    }

    @Override
    public List<Object[]> getMonthlyRevenue(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findMonthlyRevenue(startDate, endDate);
    }

    @Override
    public List<Object[]> getTopCustomersByRevenue(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findTopCustomersByRevenue(startDate, endDate);
    }
}