package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.api.dto.CategoryDtos;

import java.util.List;

public interface CategoryService {
    CategoryDtos.CategoryResponse create(CategoryDtos.CategoryCreateRequest req);
    CategoryDtos.CategoryResponse get(Long id);
    List<CategoryDtos.CategoryResponse> list();
    void delete(Long id);
}