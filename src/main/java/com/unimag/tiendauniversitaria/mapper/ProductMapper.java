package com.unimag.tiendauniversitaria.mapper;

import com.unimag.tiendauniversitaria.dto.response.ProductResponseDto;
import com.unimag.tiendauniversitaria.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entidades Product a DTOs consolidados de respuesta
 * Proporciona métodos estáticos para mapeo sin necesidad de componentes
 */
public class ProductMapper {

    private ProductMapper() {
        // Clase utilitaria, no instanciable
    }

    /**
     * Convierte una entidad Product a ProductResponseDto
     * Incluye el mapeo del inventario si existe
     *
     * @param product la entidad Product a convertir
     * @return ProductResponseDto con todos los datos mapeados
     */
    public static ProductResponseDto toResponse(Product product) {
        if (product == null) {
            return null;
        }

        return ProductResponseDto.builder()
                .productId(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .active(product.getActive())
                .availableStock(product.getInventory() != null ? product.getInventory().getAvailableStock() : null)
                .minimumStock(product.getInventory() != null ? product.getInventory().getMinimumStock() : null)
                .createdAt(product.getCreatedAt())
                .build();
    }

    /**
     * Convierte una lista de productos a una lista de ProductResponseDto
     * Útil para operaciones que retornan múltiples productos
     *
     * @param products lista de entidades Product
     * @return lista de ProductResponseDto mapeados
     */
    public static List<ProductResponseDto> toResponseList(List<Product> products) {
        if (products == null) {
            return null;
        }

        return products.stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una lista de productos a ProductResponse (Legacy)
     * Mantenido para compatibilidad temporal
     *
     * @param products lista de entidades Product
     * @return lista de ProductResponseDto mapeados
     */
    public static List<ProductResponseDto> toProductList(List<Product> products) {
        return toResponseList(products);
    }
}
