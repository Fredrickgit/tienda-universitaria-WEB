package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.api.dto.ProductDtos;
import com.unimag.tiendauniversitaria.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductDtos.ProductResponse create(ProductDtos.ProductCreateRequest req);
    ProductDtos.ProductResponse get(Long id);
    List<ProductDtos.ProductResponse> list();
    Page<ProductDtos.ProductResponse> list(Pageable pageable);
    ProductDtos.ProductResponse update(Long id, ProductDtos.ProductUpdateRequest req);
    void delete(Long id);

}