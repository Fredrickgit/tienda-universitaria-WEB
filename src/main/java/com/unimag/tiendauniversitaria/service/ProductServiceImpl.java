package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.exception.NotFoundException;
import com.unimag.tiendauniversitaria.api.dto.ProductDtos;
import com.unimag.tiendauniversitaria.entity.Product;
import com.unimag.tiendauniversitaria.entity.Category;
import com.unimag.tiendauniversitaria.repository.ProductRepository;
import com.unimag.tiendauniversitaria.repository.CategoryRepository;
import com.unimag.tiendauniversitaria.repository.OrderItemRepository;
import com.unimag.tiendauniversitaria.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {


    private final ProductRepository repo;
    private final CategoryRepository categoryRepo;

    @Override
    public ProductDtos.ProductResponse create(ProductDtos.ProductCreateRequest req) {
        var category = categoryRepo.findById(req.categoryId())
                .orElseThrow(() -> new NotFoundException("Category %d not found".formatted(req.categoryId())));

        var entity = ProductMapper.toEntity(req, category);
        var saved = repo.save(entity);

        return ProductMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDtos.ProductResponse get(Long id) {
        return repo.findById(id)
                .map(ProductMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Product %d not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDtos.ProductResponse> list() {
        return repo.findAll().stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    /*
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderItemRepository orderItemRepository;
    private final InventoryService inventoryService;

    @Override
    @Transactional
    public Product createProduct(String sku, String name, String description, BigDecimal price, Long categoryId) {
        // Validación: SKU único
        if (productRepository.existsBySku(sku)) {
            throw new IllegalArgumentException("Product with SKU '" + sku + "' already exists");
        }

        // Validación: precio mayor que cero
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }

        // Validación: categoría existente
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category with ID " + categoryId + " does not exist"));

        Product product = Product.builder()
                .sku(sku)
                .name(name)
                .description(description)
                .price(price)
                .active(true)
                .category(category)
                .build();

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product createProductWithInventory(String sku, String name, String description, BigDecimal price,
                                            Long categoryId, Integer initialStock, Integer minimumStock) {
        // Crear el producto primero
        Product product = createProduct(sku, name, description, price, categoryId);

        // Crear inventario inicial
        inventoryService.createInventoryForProduct(product.getId(), initialStock, minimumStock);

        // Recargar el producto con el inventario
        return productRepository.findById(product.getId()).orElse(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return productRepository.findBySku(sku);
    }

    @Override
    public boolean existsBySku(String sku) {
        return productRepository.existsBySku(sku);
    }

    @Override
    @Transactional
    public Product updateProduct(Long productId, String name, String description, BigDecimal price, Long categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + productId + " not found"));

        // Validación: precio mayor que cero
        if (price != null && price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }

        // Validación: categoría existente (si se proporciona)
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Category with ID " + categoryId + " does not exist"));
            product.setCategory(category);
        }

        // Actualizar campos no nulos
        if (name != null) product.setName(name);
        if (description != null) product.setDescription(description);
        if (price != null) product.setPrice(price);

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product setProductActive(Long productId, boolean active) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + productId + " not found"));

        // Si se está desactivando, verificar que no tenga pedidos activos
        if (!active) {
            boolean hasActiveOrders = orderItemRepository.findByProductId(productId)
                    .stream()
                    .anyMatch(orderItem -> {
                        String status = orderItem.getOrder().getStatus().name();
                        return "CREATED".equals(status) || "PAID".equals(status) || "SHIPPED".equals(status);
                    });

            if (hasActiveOrders) {
                throw new IllegalArgumentException("Cannot deactivate product with active orders");
            }
        }

        product.setActive(active);
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByCategory(Long categoryId) {
        // Validar que la categoría existe
        if (!categoryRepository.existsById(categoryId)) {
            throw new IllegalArgumentException("Category with ID " + categoryId + " does not exist");
        }

        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> findActiveProducts() {
        return productRepository.findByActiveTrue();
    }
    */
}