package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.entity.Category;
import com.unimag.tiendauniversitaria.entity.Inventory;
import com.unimag.tiendauniversitaria.entity.Product;
import com.unimag.tiendauniversitaria.repository.CategoryRepository;
import com.unimag.tiendauniversitaria.repository.InventoryRepository;
import com.unimag.tiendauniversitaria.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class InventoryServiceTest {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        // Crear categoría de prueba
        Category category = Category.builder()
                .name("Test Category")
                .description("Category for testing")
                .build();
        category = categoryRepository.save(category);

        // Crear producto de prueba
        testProduct = Product.builder()
                .sku("TEST-001")
                .name("Test Product")
                .description("Product for testing")
                .price(BigDecimal.valueOf(100.00))
                .active(true)
                .category(category)
                .build();
        testProduct = productRepository.save(testProduct);

        // Crear inventario de prueba
        inventoryService.updateAvailableStock(testProduct.getId(), 50);
        inventoryService.updateMinimumStock(testProduct.getId(), 10);
    }

    @Test
    void shouldFindInventoryByProductId() {
        // When
        var result = inventoryService.findByProductId(testProduct.getId());

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getAvailableStock()).isEqualTo(50);
        assertThat(result.get().getMinimumStock()).isEqualTo(10);
    }

    @Test
    void shouldFindInventoryByProductSku() {
        // When
        var result = inventoryService.findByProductSku("TEST-001");

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getProduct().getSku()).isEqualTo("TEST-001");
    }

    @Test
    void shouldUpdateAvailableStock() {
        // When
        var updated = inventoryService.updateAvailableStock(testProduct.getId(), 75);

        // Then
        assertThat(updated.getAvailableStock()).isEqualTo(75);
        var retrieved = inventoryService.findByProductId(testProduct.getId());
        assertThat(retrieved.get().getAvailableStock()).isEqualTo(75);
    }

    @Test
    void shouldUpdateMinimumStock() {
        // When
        var updated = inventoryService.updateMinimumStock(testProduct.getId(), 20);

        // Then
        assertThat(updated.getMinimumStock()).isEqualTo(20);
        var retrieved = inventoryService.findByProductId(testProduct.getId());
        assertThat(retrieved.get().getMinimumStock()).isEqualTo(20);
    }

    @Test
    void shouldIncrementStock() {
        // When
        var updated = inventoryService.incrementStock(testProduct.getId(), 25);

        // Then
        assertThat(updated.getAvailableStock()).isEqualTo(75);
    }

    @Test
    void shouldDecrementStock() {
        // When
        var updated = inventoryService.decrementStock(testProduct.getId(), 20);

        // Then
        assertThat(updated.getAvailableStock()).isEqualTo(30);
    }

    @Test
    void shouldThrowExceptionWhenDecrementingMoreThanAvailable() {
        // When & Then
        assertThatThrownBy(() -> inventoryService.decrementStock(testProduct.getId(), 60))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Insufficient stock");
    }

    @Test
    void shouldDetectLowStock() {
        // When - stock is 50, minimum is 10, so not low stock
        boolean isLow = inventoryService.isLowStock(testProduct.getId());

        // Then
        assertThat(isLow).isFalse();

        // When - set stock below minimum
        inventoryService.updateAvailableStock(testProduct.getId(), 5);
        isLow = inventoryService.isLowStock(testProduct.getId());

        // Then
        assertThat(isLow).isTrue();
    }

    @Test
    void shouldGetAvailableStock() {
        // When
        Integer stock = inventoryService.getAvailableStock(testProduct.getId());

        // Then
        assertThat(stock).isEqualTo(50);
    }

    @Test
    void shouldReturnZeroForNonExistentProductStock() {
        // When
        Integer stock = inventoryService.getAvailableStock(999L);

        // Then
        assertThat(stock).isEqualTo(0);
    }
}