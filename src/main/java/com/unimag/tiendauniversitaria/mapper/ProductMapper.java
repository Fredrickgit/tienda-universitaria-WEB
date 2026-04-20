package com.unimag.tiendauniversitaria.mapper;

import com.unimag.tiendauniversitaria.dto.response.InventoryResponse;
import com.unimag.tiendauniversitaria.dto.response.ProductResponse;
import com.unimag.tiendauniversitaria.entity.Inventory;
import com.unimag.tiendauniversitaria.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entidades Product a DTOs ProductResponse
 * Proporciona métodos estáticos para mapeo sin necesidad de componentes
 */
public class ProductMapper {

    private ProductMapper() {
        // Clase utilitaria, no instanciable
    }

    /**
     * Convierte una entidad Product a ProductResponse
     * Incluye el mapeo del inventario si existe
     *
     * @param product la entidad Product a convertir
     * @return ProductResponse con todos los datos mapeados
     */
    public static ProductResponse toResponse(Product product) {
        if (product == null) {
            return null;
        }

        return ProductResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .active(product.getActive())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .inventory(toInventoryResponse(product.getInventory()))
                .createdAt(product.getCreatedAt())
                .build();
    }

    /**
     * Convierte una entidad Inventory a InventoryResponse
     * Maneja el caso cuando el inventario es nulo
     *
     * @param inventory la entidad Inventory a convertir (puede ser null)
     * @return InventoryResponse mapeado, o null si el inventario es nulo
     */
    public static InventoryResponse toInventoryResponse(Inventory inventory) {
        if (inventory == null) {
            return null;
        }

        return InventoryResponse.builder()
                .availableStock(inventory.getAvailableStock())
                .minimumStock(inventory.getMinimumStock())
                .updatedAt(inventory.getUpdatedAt())
                .build();
    }

    /**
     * Convierte una lista de productos a una lista de ProductResponse
     * Útil para operaciones que retornan múltiples productos
     *
     * @param products lista de entidades Product
     * @return lista de ProductResponse mapeados
     */
    public static List<ProductResponse> toProductList(List<Product> products) {
        if (products == null) {
            return null;
        }

        return products.stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }
}
