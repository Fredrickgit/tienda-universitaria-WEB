package com.unimag.tiendauniversitaria.mapper;

import com.unimag.tiendauniversitaria.dto.response.InventoryResponseDto;
import com.unimag.tiendauniversitaria.entity.Inventory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entidades Inventory a DTOs de respuesta consolidados
 * Proporciona métodos estáticos para mapeo sin necesidad de componentes
 */
public class InventoryMapper {

    private InventoryMapper() {
        // Clase utilitaria, no instanciable
    }

    /**
     * Convierte una entidad Inventory a InventoryResponseDto
     * Incluye información del producto asociado y fechas
     *
     * @param inventory la entidad Inventory a convertir
     * @return InventoryResponseDto con todos los datos mapeados
     */
    public static InventoryResponseDto toResponse(Inventory inventory) {
        if (inventory == null) {
            return null;
        }

        return InventoryResponseDto.builder()
                .inventoryId(inventory.getId())
                .productId(inventory.getProduct() != null ? inventory.getProduct().getId() : null)
                .productName(inventory.getProduct() != null ? inventory.getProduct().getName() : null)
                .availableStock(inventory.getAvailableStock())
                .minimumStock(inventory.getMinimumStock())
                .updatedAt(inventory.getUpdatedAt())
                .build();
    }

    /**
     * Convierte una lista de inventarios a una lista de InventoryResponseDto
     * Útil para operaciones que retornan múltiples inventarios
     *
     * @param inventories lista de entidades Inventory
     * @return lista de InventoryResponseDto mapeados
     */
    public static List<InventoryResponseDto> toResponseList(List<Inventory> inventories) {
        if (inventories == null) {
            return null;
        }

        return inventories.stream()
                .map(InventoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una entidad Inventory a InventoryDetailsResponse (Legacy)
     * Incluye indicador de stock bajo - Mantenido para compatibilidad
     *
     * @param inventory la entidad Inventory a convertir
     * @param isLowStock indicador si el inventario tiene stock bajo
     * @return InventoryResponseDto con todos los datos mapeados
     */
    public static InventoryResponseDto toDetailsResponse(Inventory inventory, Boolean isLowStock) {
        return toResponse(inventory);
    }

    /**
     * Convierte una entidad Inventory a InventoryDetailsResponse (Legacy)
     * Calcula automáticamente si es stock bajo - Mantenido para compatibilidad
     *
     * @param inventory la entidad Inventory a convertir
     * @return InventoryResponseDto con stock bajo calculado
     */
    public static InventoryResponseDto toDetailsResponse(Inventory inventory) {
        return toResponse(inventory);
    }

    /**
     * Convierte una lista de inventarios a InventoryDetailsResponse (Legacy)
     * Mantenido para compatibilidad
     *
     * @param inventories lista de entidades Inventory
     * @return lista de InventoryResponseDto mapeados
     */
    public static List<InventoryResponseDto> toDetailsList(List<Inventory> inventories) {
        return toResponseList(inventories);
    }
}
