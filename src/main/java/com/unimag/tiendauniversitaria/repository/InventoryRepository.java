package com.unimag.tiendauniversitaria.repository;

import com.unimag.tiendauniversitaria.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductId(Long productId);

    Optional<Inventory> findByProductSku(String sku);

    @Query("select i from Inventory i where i.availableStock < i.minimumStock")
    List<Inventory> findLowStockInventories();
}
