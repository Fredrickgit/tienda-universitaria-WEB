package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.api.dto.CategoryDtos;
import com.unimag.tiendauniversitaria.exception.NotFoundException;
import com.unimag.tiendauniversitaria.repository.CategoryRepository;
import com.unimag.tiendauniversitaria.service.mapper.CategoryMapper;
import jakarta.transaction.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.*;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repo;

    @Override
    public CategoryDtos.CategoryResponse create(CategoryDtos.CategoryCreateRequest req) {
        var entity = CategoryMapper.toEntity(req);
        var saved = repo.save(entity);
        return CategoryMapper.toResponse(saved);
    }

    ///readonly?
    @Override
    @Transactional
    public CategoryDtos.CategoryResponse get(Long id) {
        return repo.findById(id)
                .map(CategoryMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Category %d not found".formatted(id)));
    }

    @Override
    @Transactional
    public List<CategoryDtos.CategoryResponse> list() {
        return repo.findAll().stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}