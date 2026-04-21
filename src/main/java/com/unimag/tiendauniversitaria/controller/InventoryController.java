package com.unimag.tiendauniversitaria.controller;

import com.unimag.tiendauniversitaria.dto.ApiResponse;
import com.unimag.tiendauniversitaria.dto.request.InventoryRequest;
import com.unimag.tiendauniversitaria.dto.response.InventoryResponseDto;
import com.unimag.tiendauniversitaria.entity.Inventory;
import com.unimag.tiendauniversitaria.mapper.InventoryMapper;
import com.unimag.tiendauniversitaria.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de inventarios
 * Expone los servicios de InventoryService a través de endpoints HTTP
 */
@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * POST /api/inventories
     * Crea inventario inicial para un producto
     *
     * @param request DTO con productId, initialStock y minimumStock
     * @return ResponseEntity con InventoryResponseDto y status 201 CREATED
     */
    @PostMapping
    public ResponseEntity<ApiResponse<InventoryResponseDto>> createInventory(
            @Valid @RequestBody InventoryRequest request) {
        
        Inventory inventory = inventoryService.createInventoryForProduct(
                request.getProductId(),
                request.getInitialStock(),
                request.getMinimumStock()
        );
        
        InventoryResponseDto response = InventoryMapper.toResponse(inventory);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ofSuccess(response, "Inventory created successfully"));
    }

    /**
     * GET /api/inventories/product/{productId}
     * Obtiene el inventario de un producto por su ID
     *
     * @param productId ID del producto
     * @return ResponseEntity con InventoryResponseDto y status 200 OK
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<InventoryResponseDto>> getInventoryByProductId(
            @PathVariable Long productId) {
        
        Inventory inventory = inventoryService.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for product ID: " + productId));
        
        InventoryResponseDto response = InventoryMapper.toResponse(inventory);
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Inventory found"));
    }

    /**
     * GET /api/inventories/sku/{sku}
     * Obtiene el inventario de un producto por SKU
     *
     * @param sku SKU del producto
     * @return ResponseEntity con InventoryResponseDto y status 200 OK
     */
    @GetMapping("/sku/{sku}")
    public ResponseEntity<ApiResponse<InventoryResponseDto>> getInventoryByProductSku(
            @PathVariable String sku) {
        
        Inventory inventory = inventoryService.findByProductSku(sku)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for product SKU: " + sku));
        
        InventoryResponseDto response = InventoryMapper.toResponse(inventory);
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Inventory found"));
    }

    /**
     * GET /api/inventories/low-stock
     * Obtiene todos los inventarios con stock bajo
     *
     * @return ResponseEntity con lista de InventoryResponseDto con stock bajo
     */
    @GetMapping("/low-stock")
    public ResponseEntity<ApiResponse<List<InventoryResponseDto>>> getLowStockInventories() {
        List<Inventory> inventories = inventoryService.getLowStockInventories();
        List<InventoryResponseDto> responses = InventoryMapper.toResponseList(inventories);
        return ResponseEntity.ok(ApiResponse.ofSuccess(responses, "Low stock inventories retrieved successfully"));
    }

    /**
     * PUT /api/inventories/product/{productId}/available-stock
     * Actualiza el stock disponible de un producto
     *
     * @param productId ID del producto
     * @param request DTO con availableStock
     * @return ResponseEntity con InventoryResponseDto actualizado
     */
    @PutMapping("/product/{productId}/available-stock")
    public ResponseEntity<ApiResponse<InventoryResponseDto>> updateAvailableStock(
            @PathVariable Long productId,
            @Valid @RequestBody InventoryRequest request) {
        
        Inventory inventory = inventoryService.updateAvailableStock(productId, request.getAvailableStock());
        InventoryResponseDto response = InventoryMapper.toResponse(inventory);
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Available stock updated successfully"));
    }

    /**
     * PUT /api/inventories/product/{productId}/minimum-stock
     * Actualiza el stock mínimo de un producto
     *
     * @param productId ID del producto
     * @param request DTO con minimumStock
     * @return ResponseEntity con InventoryResponseDto actualizado
     */
    @PutMapping("/product/{productId}/minimum-stock")
    public ResponseEntity<ApiResponse<InventoryResponseDto>> updateMinimumStock(
            @PathVariable Long productId,
            @Valid @RequestBody InventoryRequest request) {
        
        Inventory inventory = inventoryService.updateMinimumStock(productId, request.getMinimumStock());
        InventoryResponseDto response = InventoryMapper.toResponse(inventory);
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Minimum stock updated successfully"));
    }

    /**
     * POST /api/inventories/product/{productId}/increment
     * Incrementa el stock disponible
     *
     * @param productId ID del producto
     * @param request DTO con quantity a incrementar
     * @return ResponseEntity con InventoryResponseDto actualizado
     */
    @PostMapping("/product/{productId}/increment")
    public ResponseEntity<ApiResponse<InventoryResponseDto>> incrementStock(
            @PathVariable Long productId,
            @Valid @RequestBody InventoryRequest request) {
        
        Inventory inventory = inventoryService.incrementStock(productId, request.getQuantity());
        InventoryResponseDto response = InventoryMapper.toResponse(inventory);
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Stock incremented successfully"));
    }

    /**
     * POST /api/inventories/product/{productId}/decrement
     * Decrementa el stock disponible
     *
     * @param productId ID del producto
     * @param request DTO con quantity a decrementar
     * @return ResponseEntity con InventoryResponseDto actualizado
     */
    @PostMapping("/product/{productId}/decrement")
    public ResponseEntity<ApiResponse<InventoryResponseDto>> decrementStock(
            @PathVariable Long productId,
            @Valid @RequestBody InventoryRequest request) {
        
        Inventory inventory = inventoryService.decrementStock(productId, request.getQuantity());
        InventoryResponseDto response = InventoryMapper.toResponse(inventory);
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Stock decremented successfully"));
    }

    /**
     * GET /api/inventories/product/{productId}/is-low-stock
     * Verifica si un producto tiene stock bajo
     *
     * @param productId ID del producto
     * @return ResponseEntity con valor booleano indicando si es stock bajo
     */
    @GetMapping("/product/{productId}/is-low-stock")
    public ResponseEntity<ApiResponse<Boolean>> isLowStock(@PathVariable Long productId) {
        boolean lowStock = inventoryService.isLowStock(productId);
        return ResponseEntity.ok(ApiResponse.ofSuccess(lowStock, 
                lowStock ? "Product has low stock" : "Product has sufficient stock"));
    }

    /**
     * GET /api/inventories/product/{productId}/available-stock
     * Obtiene el stock disponible actual de un producto
     *
     * @param productId ID del producto
     * @return ResponseEntity con cantidad de stock disponible
     */
    @GetMapping("/product/{productId}/available-stock")
    public ResponseEntity<ApiResponse<Integer>> getAvailableStock(@PathVariable Long productId) {
        Integer availableStock = inventoryService.getAvailableStock(productId);
        return ResponseEntity.ok(ApiResponse.ofSuccess(availableStock, "Available stock retrieved successfully"));
    }
}
