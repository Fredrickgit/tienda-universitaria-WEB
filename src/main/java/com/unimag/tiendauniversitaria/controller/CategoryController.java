package com.unimag.tiendauniversitaria.controller;

import com.unimag.tiendauniversitaria.dto.ApiResponse;
import com.unimag.tiendauniversitaria.dto.response.CategoryResponseDto;
import com.unimag.tiendauniversitaria.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponseDto>>> getAllCategories() {
        List<CategoryResponseDto> categories = categoryRepository.findAll()
                .stream()
                .map(category -> CategoryResponseDto.builder()
                        .categoryId(category.getId())
                        .name(category.getName())
                        .description(category.getDescription())
                        .build())
                .toList();

        return ResponseEntity.ok(ApiResponse.ofSuccess(categories, "Categories retrieved successfully"));
    }
}
