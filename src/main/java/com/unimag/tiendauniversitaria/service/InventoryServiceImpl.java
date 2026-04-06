package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.entity.Inventory;
import com.unimag.tiendauniversitaria.entity.Product;
import com.unimag.tiendauniversitaria.repository.InventoryRepository;
import com.unimag.tiendauniversitaria.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    @Override
    public Optional<Inventory> findByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId);
    }

    @Override
    public Optional<Inventory> findByProductSku(String sku) {
        return inventoryRepository.findByProductSku(sku);
    }

    @Override
    public List<Inventory> getLowStockInventories() {
        return inventoryRepository.findLowStockInventories();
    }

    @Override
    @Transactional
    public Inventory updateAvailableStock(Long productId, Integer newStock) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for product ID: " + productId));

        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        inventory.setAvailableStock(newStock);
        return inventoryRepository.save(inventory);
    }

    @Override
    @Transactional
    public Inventory updateMinimumStock(Long productId, Integer newMinimumStock) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for product ID: " + productId));

        if (newMinimumStock < 0) {
            throw new IllegalArgumentException("Minimum stock cannot be negative");
        }

        inventory.setMinimumStock(newMinimumStock);
        return inventoryRepository.save(inventory);
    }

    @Override
    @Transactional
    public Inventory incrementStock(Long productId, Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for product ID: " + productId));

        inventory.setAvailableStock(inventory.getAvailableStock() + quantity);
        return inventoryRepository.save(inventory);
    }

    @Override
    @Transactional
    public Inventory decrementStock(Long productId, Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for product ID: " + productId));

        int newStock = inventory.getAvailableStock() - quantity;
        if (newStock < 0) {
            throw new IllegalArgumentException("Insufficient stock. Available: " + inventory.getAvailableStock() + ", requested: " + quantity);
        }

        inventory.setAvailableStock(newStock);
        return inventoryRepository.save(inventory);
    }

    @Override
    public boolean isLowStock(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .map(inventory -> inventory.getAvailableStock() < inventory.getMinimumStock())
                .orElse(false);
    }

    @Override
    public Integer getAvailableStock(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .map(Inventory::getAvailableStock)
                .orElse(0);
    }
}