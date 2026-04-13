package com.unimag.tiendauniversitaria.service.mapper;

import com.unimag.tiendauniversitaria.api.dto.InventoryDtos;
import com.unimag.tiendauniversitaria.entity.Inventory;
import com.unimag.tiendauniversitaria.entity.Product;

public class InventoryMapper {

    public static Inventory toEntity(InventoryDtos.InventoryCreateRequest req, Product product) {
        Inventory i = new Inventory();
        i.setProduct(product);
        i.setAvailableStock(req.quantity());
        return i;
    }

    public static InventoryDtos.InventoryResponse toResponse(Inventory i) {
        return new InventoryDtos.InventoryResponse(
                i.getId(),
                i.getProduct().getId(),
                i.getAvailableStock()
        );
    }
}