package com.unimag.tiendauniversitaria.api;

import com.unimag.tiendauniversitaria.api.dto.AddressDtos;
import com.unimag.tiendauniversitaria.api.dto.CategoryDtos;
import com.unimag.tiendauniversitaria.api.dto.CustomerDtos;
import com.unimag.tiendauniversitaria.api.dto.InventoryDtos;
import com.unimag.tiendauniversitaria.service.AddressService;
import com.unimag.tiendauniversitaria.service.CategoryService;
import com.unimag.tiendauniversitaria.service.CustomerService;
import com.unimag.tiendauniversitaria.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Validated
public class InventoryController {

    private final InventoryService service;

    @PostMapping
    public ResponseEntity<InventoryDtos.InventoryResponse> create(@Valid @RequestBody InventoryDtos.InventoryCreateRequest req,
                                                                  UriComponentsBuilder uriBuilder) {
        var body = service.create(req);
        var location = uriBuilder.path("/api/v1/inventory/{id}")
                .buildAndExpand(body.id()).toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDtos.InventoryResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<InventoryDtos.InventoryResponse>> list(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        var result = service.list(PageRequest.of(page, size, Sort.by("id").ascending()));
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<InventoryDtos.InventoryResponse> update(@PathVariable Long id,
                                                                  @Valid @RequestBody InventoryDtos.InventoryUpdateRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}