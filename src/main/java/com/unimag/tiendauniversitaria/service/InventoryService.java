package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.api.dto.InventoryDtos;
import com.unimag.tiendauniversitaria.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface InventoryService {

        InventoryDtos.InventoryResponse create(InventoryDtos.InventoryCreateRequest req);
        InventoryDtos.InventoryResponse get(Long id);
        List<InventoryDtos.InventoryResponse> list();
        Page<InventoryDtos.InventoryResponse> list(Pageable pageable);
        InventoryDtos.InventoryResponse update(Long id, InventoryDtos.InventoryUpdateRequest req);
        void delete(Long id);

}