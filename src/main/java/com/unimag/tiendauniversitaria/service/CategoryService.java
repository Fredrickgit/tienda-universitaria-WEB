package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.api.dto.CategoryDtos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryDtos.CategoryResponse create(CategoryDtos.CategoryCreateRequest req);
    CategoryDtos.CategoryResponse get(Long id);
    List<CategoryDtos.CategoryResponse> list();
    Page<CategoryDtos.CategoryResponse> list(Pageable pageable);
    CategoryDtos.CategoryResponse update(Long id, CategoryDtos.CategoryUpdateRequest req);
    void delete(Long id);
}