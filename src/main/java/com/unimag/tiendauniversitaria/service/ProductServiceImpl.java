package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.exception.NotFoundException;
import com.unimag.tiendauniversitaria.api.dto.ProductDtos;
import com.unimag.tiendauniversitaria.entity.Product;
import com.unimag.tiendauniversitaria.entity.Category;
import com.unimag.tiendauniversitaria.repository.ProductRepository;
import com.unimag.tiendauniversitaria.repository.CategoryRepository;
import com.unimag.tiendauniversitaria.repository.OrderItemRepository;
import com.unimag.tiendauniversitaria.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {


    private final ProductRepository repo;
    private final CategoryRepository categoryRepo;

    @Override
    public ProductDtos.ProductResponse create(ProductDtos.ProductCreateRequest req) {
        var category = categoryRepo.findById(req.categoryId())
                .orElseThrow(() -> new NotFoundException("Category %d not found".formatted(req.categoryId())));

        var entity = ProductMapper.toEntity(req, category);
        var saved = repo.save(entity);

        return ProductMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDtos.ProductResponse get(Long id) {
        return repo.findById(id)
                .map(ProductMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Product %d not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDtos.ProductResponse> list() {
        return repo.findAll().stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDtos.ProductResponse> list(Pageable pageable) {
        return repo.findAll(pageable)
                .map(ProductMapper::toResponse);
    }

    @Override
    public ProductDtos.ProductResponse update(Long id, ProductDtos.ProductUpdateRequest req) {
        var product = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Product %d not found".formatted(id)));

        var category = categoryRepo.findById(req.categoryId())
                .orElseThrow(() -> new NotFoundException("Category %d not found".formatted(req.categoryId())));

        ProductMapper.updateEntity(product, req, category);
        var saved = repo.save(product);

        return ProductMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

}