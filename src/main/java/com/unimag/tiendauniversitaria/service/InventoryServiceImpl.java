package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.api.dto.InventoryDtos;
import com.unimag.tiendauniversitaria.entity.Inventory;
import com.unimag.tiendauniversitaria.entity.Product;
import com.unimag.tiendauniversitaria.exception.NotFoundException;
import com.unimag.tiendauniversitaria.repository.InventoryRepository;
import com.unimag.tiendauniversitaria.repository.ProductRepository;
import com.unimag.tiendauniversitaria.service.mapper.InventoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

    @Service
    @RequiredArgsConstructor
    @Transactional
    public class InventoryServiceImpl implements InventoryService {

        private final InventoryRepository repo;
        private final ProductRepository productRepo;

        @Override
        public InventoryDtos.InventoryResponse create(InventoryDtos.InventoryCreateRequest req) {
            var product = productRepo.findById(req.productId())
                    .orElseThrow(() -> new NotFoundException("Product %d not found".formatted(req.productId())));

            var entity = InventoryMapper.toEntity(req, product);
            var saved = repo.save(entity);

            return InventoryMapper.toResponse(saved);
        }

        @Override
        @Transactional(readOnly = true)
        public InventoryDtos.InventoryResponse get(Long id) {
            return repo.findById(id)
                    .map(InventoryMapper::toResponse)
                    .orElseThrow(() -> new NotFoundException("Inventory %d not found".formatted(id)));
        }

        @Override
        @Transactional(readOnly = true)
        public List<InventoryDtos.InventoryResponse> list() {
            return repo.findAll().stream()
                    .map(InventoryMapper::toResponse)
                    .toList();
        }

        @Override
        public void delete(Long id) {
            repo.deleteById(id);
        }

    /*
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
    public Inventory createInventoryForProduct(Long productId, Integer initialStock, Integer minimumStock) {
        // Validar valores de stock
        validateStockValues(initialStock, minimumStock);

        // Verificar que el producto existe
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + productId + " not found"));

        // Verificar que no exista ya inventario para este producto
        if (inventoryRepository.findByProductId(productId).isPresent()) {
            throw new IllegalArgumentException("Inventory already exists for product ID: " + productId);
        }

        Inventory inventory = Inventory.builder()
                .availableStock(initialStock)
                .minimumStock(minimumStock)
                .product(product)
                .build();

        return inventoryRepository.save(inventory);
    }

    @Override
    @Transactional
    public Inventory updateAvailableStock(Long productId, Integer newStock) {
        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for product ID: " + productId));

        inventory.setAvailableStock(newStock);
        return inventoryRepository.save(inventory);
    }

    @Override
    @Transactional
    public Inventory updateMinimumStock(Long productId, Integer newMinimumStock) {
        if (newMinimumStock < 0) {
            throw new IllegalArgumentException("Minimum stock cannot be negative");
        }

        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for product ID: " + productId));

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

    @Override
    public void validateStockValues(Integer availableStock, Integer minimumStock) {
        if (availableStock < 0) {
            throw new IllegalArgumentException("Available stock cannot be negative");
        }
        if (minimumStock < 0) {
            throw new IllegalArgumentException("Minimum stock cannot be negative");
        }
    }

     */
}