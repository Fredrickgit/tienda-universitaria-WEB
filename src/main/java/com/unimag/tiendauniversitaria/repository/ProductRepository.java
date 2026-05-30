package com.unimag.tiendauniversitaria.repository;

import com.unimag.tiendauniversitaria.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    boolean existsBySku(String sku);

    List<Product> findByActiveTrue();

    List<Product> findByCategoryId(Long categoryId);

    // Buscar productos activos por categoría
    List<Product> findByCategoryIdAndActiveTrue(Long categoryId);

    // Productos más vendidos por período
    @Query("SELECT oi.product FROM OrderItem oi " +
           "JOIN oi.order o " +
           "WHERE o.status IN (com.unimag.tiendauniversitaria.enums.OrderStatus.PAID, " +
           "com.unimag.tiendauniversitaria.enums.OrderStatus.SHIPPED, " +
           "com.unimag.tiendauniversitaria.enums.OrderStatus.DELIVERED) " +
           "AND o.createdAt BETWEEN :startDate AND :endDate " +
           "GROUP BY oi.product " +
           "ORDER BY SUM(oi.quantity) DESC")
    List<Product> findTopSellingProductsByPeriod(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    // Productos con stock insuficiente (esto ya está en InventoryRepository)
    // pero aquí podríamos tener una versión que incluya el producto
}
