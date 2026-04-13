package com.unimag.tiendauniversitaria.service.mapper;

import com.unimag.tiendauniversitaria.api.dto.CategoryDtos;
import com.unimag.tiendauniversitaria.entity.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryDtos.CategoryCreateRequest req) {
        Category c = new Category();
        c.setName(req.name());
        return c;
    }

    public static CategoryDtos.CategoryResponse toResponse(Category c) {
        return new CategoryDtos.CategoryResponse(c.getId(), c.getName());
    }
}