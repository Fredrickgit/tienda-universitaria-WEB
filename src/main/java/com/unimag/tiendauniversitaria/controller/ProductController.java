package com.unimag.tiendauniversitaria.controller;

import com.unimag.tiendauniversitaria.dto.ApiResponse;
import com.unimag.tiendauniversitaria.dto.request.ProductRequest;
import com.unimag.tiendauniversitaria.dto.response.ProductResponseDto;
import com.unimag.tiendauniversitaria.entity.Product;
import com.unimag.tiendauniversitaria.mapper.ProductMapper;
import com.unimag.tiendauniversitaria.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de productos
 * Expone los servicios de ProductService a través de endpoints HTTP
 * Maneja mapping entre DTOs y entidades, validación de requests
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * POST /api/products
     * Crea un nuevo producto
     *
     * @param request DTO con datos del producto a crear
     * @return ResponseEntity con ProductResponseDto y status 201 CREATED
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(
            @Valid @RequestBody ProductRequest request) {
        
        Product product = productService.createProduct(
                request.getSku(),
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getCategoryId()
        );
        
        ProductResponseDto response = ProductMapper.toResponse(product);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ofSuccess(response, "Product created successfully"));
    }

    /**
     * POST /api/products/with-inventory
     * Crea un nuevo producto con inventario inicial
     *
     * @param request DTO con datos del producto e inventario inicial
     * @return ResponseEntity con ProductResponseDto y status 201 CREATED
     */
    @PostMapping("/with-inventory")
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProductWithInventory(
            @Valid @RequestBody ProductRequest request) {
        
        Product product = productService.createProductWithInventory(
                request.getSku(),
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getCategoryId(),
                request.getInitialStock(),
                request.getMinimumStock()
        );
        
        ProductResponseDto response = ProductMapper.toResponse(product);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ofSuccess(response, "Product with inventory created successfully"));
    }

    /**
     * GET /api/products/{id}
     * Obtiene un producto por ID
     *
     * @param id ID del producto a obtener
     * @return ResponseEntity con ProductResponseDto y status 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found"));
        
        ProductResponseDto response = ProductMapper.toResponse(product);
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Product found"));
    }

    /**
     * GET /api/products/sku/{sku}
     * Obtiene un producto por SKU
     *
     * @param sku SKU del producto a obtener
     * @return ResponseEntity con ProductResponseDto y status 200 OK
     */
    @GetMapping("/sku/{sku}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProductBySku(@PathVariable String sku) {
        Product product = productService.findBySku(sku)
                .orElseThrow(() -> new IllegalArgumentException("Product with SKU '" + sku + "' not found"));
        
        ProductResponseDto response = ProductMapper.toResponse(product);
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Product found"));
    }

    /**
     * GET /api/products
     * Obtiene todos los productos
     *
     * @return ResponseEntity con lista de ProductResponseDto y status 200 OK
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllProducts() {
        List<Product> products = productService.findAll();
        List<ProductResponseDto> responses = ProductMapper.toResponseList(products);
        return ResponseEntity.ok(ApiResponse.ofSuccess(responses, "Products retrieved successfully"));
    }

    /**
     * GET /api/products/active
     * Obtiene todos los productos activos
     *
     * @return ResponseEntity con lista de ProductResponseDto activos y status 200 OK
     */
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getActiveProducts() {
        List<Product> products = productService.findActiveProducts();
        List<ProductResponseDto> responses = ProductMapper.toResponseList(products);
        return ResponseEntity.ok(ApiResponse.ofSuccess(responses, "Active products retrieved successfully"));
    }

    /**
     * GET /api/products/category/{categoryId}
     * Obtiene productos por categoría
     *
     * @param categoryId ID de la categoría
     * @return ResponseEntity con lista de ProductResponseDto de la categoría y status 200 OK
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getProductsByCategory(
            @PathVariable Long categoryId) {
        
        List<Product> products = productService.findByCategory(categoryId);
        List<ProductResponseDto> responses = ProductMapper.toResponseList(products);
        return ResponseEntity.ok(ApiResponse.ofSuccess(responses, "Products by category retrieved successfully"));
    }

    /**
     * PUT /api/products/{id}
     * Actualiza un producto existente
     *
     * @param id ID del producto a actualizar
     * @param request DTO con datos a actualizar (campos opcionales)
     * @return ResponseEntity con ProductResponseDto actualizado y status 200 OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        
        Product product = productService.updateProduct(
                id,
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getCategoryId()
        );
        
        ProductResponseDto response = ProductMapper.toResponse(product);
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Product updated successfully"));
    }

    /**
     * PATCH /api/products/{id}/status
     * Activa o desactiva un producto
     *
     * @param id ID del producto
     * @param active true para activar, false para desactivar
     * @return ResponseEntity con ProductResponseDto actualizado y status 200 OK
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ProductResponseDto>> setProductStatus(
            @PathVariable Long id,
            @RequestParam Boolean active) {
        
        Product product = productService.setProductActive(id, active);
        ProductResponseDto response = ProductMapper.toResponse(product);
        
        String message = active ? "Product activated successfully" : "Product deactivated successfully";
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, message));
    }
}
