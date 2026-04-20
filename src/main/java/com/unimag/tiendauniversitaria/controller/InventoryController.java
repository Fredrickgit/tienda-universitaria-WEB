package com.unimag.tiendauniversitaria.controller;

import com.unimag.tiendauniversitaria.dto.ApiResponse;
import com.unimag.tiendauniversitaria.dto.request.InventoryCreateRequest;
import com.unimag.tiendauniversitaria.dto.request.InventoryDecrementStockRequest;
import com.unimag.tiendauniversitaria.dto.request.InventoryIncrementStockRequest;
import com.unimag.tiendauniversitaria.dto.request.InventoryUpdateAvailableStockRequest;
import com.unimag.tiendauniversitaria.dto.request.InventoryUpdateMinimumStockRequest;
import com.unimag.tiendauniversitaria.dto.response.InventoryDetailsResponse;
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
     * @return ResponseEntity con InventoryDetailsResponse y status 201 CREATED
     */
    @PostMapping
    public ResponseEntity<ApiResponse<InventoryDetailsResponse>> createInventory(
            @Valid @RequestBody InventoryCreateRequest request) {
        
        Inventory inventory = inventoryService.createInventoryForProduct(
                request.getProductId(),
                request.getInitialStock(),
                request.getMinimumStock()
        );
        
        InventoryDetailsResponse response = InventoryMapper.toDetailsResponse(inventory);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ofSuccess(response, "Inventory created successfully"));
    }

    /**
     * GET /api/inventories/product/{productId}
     * Obtiene el inventario de un producto por su ID
     *
     * @param productId ID del producto
     * @return ResponseEntity con InventoryDetailsResponse y status 200 OK
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<InventoryDetailsResponse>> getInventoryByProductId(
            @PathVariable Long productId) {
        
        Inventory inventory = inventoryService.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for product ID: " + productId));
        
        InventoryDetailsResponse response = InventoryMapper.toDetailsResponse(inventory);
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Inventory found"));
    }

    /**
     * GET /api/inventories/sku/{sku}
     * Obtiene el inventario de un producto por SKU
     *
     * @param sku SKU del producto
     * @return ResponseEntity con InventoryDetailsResponse y status 200 OK
     */
    @GetMapping("/sku/{sku}")
    public ResponseEntity<ApiResponse<InventoryDetailsResponse>> getInventoryByProductSku(
            @PathVariable String sku) {
        
        Inventory inventory = inventoryService.findByProductSku(sku)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for product SKU: " + sku));
        
        InventoryDetailsResponse response = InventoryMapper.toDetailsResponse(inventory);
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Inventory found"));
    }

    /**
     * GET /api/inventories/low-stock
     * Obtiene todos los inventarios con stock bajo
     *
     * @return ResponseEntity con lista de InventoryDetailsResponse con stock bajo
     */
    @GetMapping("/low-stock")
    public ResponseEntity<ApiResponse<List<InventoryDetailsResponse>>> getLowStockInventories() {
        List<Inventory> inventories = inventoryService.getLowStockInventories();
        List<InventoryDetailsResponse> responses = InventoryMapper.toDetailsList(inventories);
        return ResponseEntity.ok(ApiResponse.ofSuccess(responses, "Low stock inventories retrieved successfully"));
    }

    /**
     * PUT /api/inventories/product/{productId}/available-stock
     * Actualiza el stock disponible de un producto
     *
     * @param productId ID del producto
     * @param request DTO con newStock
     * @return ResponseEntity con InventoryDetailsResponse actualizado
     */
    @PutMapping("/product/{productId}/available-stock")
    public ResponseEntity<ApiResponse<InventoryDetailsResponse>> updateAvailableStock(
            @PathVariable Long productId,
            @Valid @RequestBody InventoryUpdateAvailableStockRequest request) {
        
        Inventory inventory = inventoryService.updateAvailableStock(productId, request.getNewStock());
        InventoryDetailsResponse response = InventoryMapper.toDetailsResponse(inventory);
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Available stock updated successfully"));
    }

    /**
     * PUT /api/inventories/product/{productId}/minimum-stock
     * Actualiza el stock mínimo de un producto
     *
     * @param productId ID del producto
     * @param request DTO con newMinimumStock
     * @return ResponseEntity con InventoryDetailsResponse actualizado
     */
    @PutMapping("/product/{productId}/minimum-stock")
    public ResponseEntity<ApiResponse<InventoryDetailsResponse>> updateMinimumStock(
            @PathVariable Long productId,
            @Valid @RequestBody InventoryUpdateMinimumStockRequest request) {
        
        Inventory inventory = inventoryService.updateMinimumStock(productId, request.getNewMinimumStock());
        InventoryDetailsResponse response = InventoryMapper.toDetailsResponse(inventory);
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Minimum stock updated successfully"));
    }

    /**
     * POST /api/inventories/product/{productId}/increment
     * Incrementa el stock disponible
     *
     * @param productId ID del producto
     * @param request DTO con quantity a incrementar
     * @return ResponseEntity con InventoryDetailsResponse actualizado
     */
    @PostMapping("/product/{productId}/increment")
    public ResponseEntity<ApiResponse<InventoryDetailsResponse>> incrementStock(
            @PathVariable Long productId,
            @Valid @RequestBody InventoryIncrementStockRequest request) {
        
        Inventory inventory = inventoryService.incrementStock(productId, request.getQuantity());
        InventoryDetailsResponse response = InventoryMapper.toDetailsResponse(inventory);
        return ResponseEntity.ok(ApiResponse.ofSuccess(response, "Stock incremented successfully"));
    }

    /**
     * POST /api/inventories/product/{productId}/decrement
     * Decrementa el stock disponible
     *
     * @param productId ID del producto
     * @param request DTO con quantity a decrementar
     * @return ResponseEntity con InventoryDetailsResponse actualizado
     */
    @PostMapping("/product/{productId}/decrement")
    public ResponseEntity<ApiResponse<InventoryDetailsResponse>> decrementStock(
            @PathVariable Long productId,
            @Valid @RequestBody InventoryDecrementStockRequest request) {
        
        Inventory inventory = inventoryService.decrementStock(productId, request.getQuantity());
        InventoryDetailsResponse response = InventoryMapper.toDetailsResponse(inventory);
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
