package com.unimag.tiendauniversitaria.service.mapper;

import com.unimag.tiendauniversitaria.api.dto.ProductDtos;
import com.unimag.tiendauniversitaria.entity.Category;
import com.unimag.tiendauniversitaria.entity.Product;

public class ProductMapper {

    public static Product toEntity(ProductDtos.ProductCreateRequest req, Category category) {
        Product p = new Product();
        p.setName(req.name());
        p.setPrice(req.price());
        p.setCategory(category);
        return p;
    }

    public static void updateEntity(Product product, ProductDtos.ProductUpdateRequest req, Category category) {
        product.setName(req.name());
        product.setPrice(req.price());
        product.setCategory(category);
    }

    public static ProductDtos.ProductResponse toResponse(Product p) {
        return new ProductDtos.ProductResponse(
                p.getId(),
                p.getName(),
                p.getPrice(),
                p.getCategory().getName()
        );
    }
}