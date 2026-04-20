package com.unimag.tiendauniversitaria.mapper;

import com.unimag.tiendauniversitaria.dto.response.InventoryDetailsResponse;
import com.unimag.tiendauniversitaria.entity.Inventory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entidades Inventory a DTOs de respuesta
 * Proporciona métodos estáticos para mapeo sin necesidad de componentes
 */
public class InventoryMapper {

    private InventoryMapper() {
        // Clase utilitaria, no instanciable
    }

    /**
     * Convierte una entidad Inventory a InventoryDetailsResponse
     * Incluye información del producto asociado
     *
     * @param inventory la entidad Inventory a convertir
     * @param isLowStock indicador si el inventario tiene stock bajo
     * @return InventoryDetailsResponse con todos los datos mapeados
     */
    public static InventoryDetailsResponse toDetailsResponse(Inventory inventory, Boolean isLowStock) {
        if (inventory == null) {
            return null;
        }

        return InventoryDetailsResponse.builder()
                .id(inventory.getId())
                .availableStock(inventory.getAvailableStock())
                .minimumStock(inventory.getMinimumStock())
                .updatedAt(inventory.getUpdatedAt())
                .productId(inventory.getProduct() != null ? inventory.getProduct().getId() : null)
                .productSku(inventory.getProduct() != null ? inventory.getProduct().getSku() : null)
                .productName(inventory.getProduct() != null ? inventory.getProduct().getName() : null)
                .isLowStock(isLowStock)
                .build();
    }

    /**
     * Convierte una entidad Inventory a InventoryDetailsResponse sin indicador de stock bajo
     * Calcula automáticamente si es stock bajo comparando disponible vs mínimo
     *
     * @param inventory la entidad Inventory a convertir
     * @return InventoryDetailsResponse con stock bajo calculado
     */
    public static InventoryDetailsResponse toDetailsResponse(Inventory inventory) {
        if (inventory == null) {
            return null;
        }

        boolean isLowStock = inventory.getAvailableStock() < inventory.getMinimumStock();
        return toDetailsResponse(inventory, isLowStock);
    }

    /**
     * Convierte una lista de inventarios a una lista de InventoryDetailsResponse
     * Útil para operaciones que retornan múltiples inventarios
     *
     * @param inventories lista de entidades Inventory
     * @return lista de InventoryDetailsResponse mapeados
     */
    public static List<InventoryDetailsResponse> toDetailsList(List<Inventory> inventories) {
        if (inventories == null) {
            return null;
        }

        return inventories.stream()
                .map(InventoryMapper::toDetailsResponse)
                .collect(Collectors.toList());
    }
}
