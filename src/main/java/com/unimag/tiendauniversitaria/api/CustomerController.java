package com.unimag.tiendauniversitaria.api;

import com.unimag.tiendauniversitaria.api.dto.CategoryDtos;
import com.unimag.tiendauniversitaria.api.dto.CustomerDtos;
import com.unimag.tiendauniversitaria.service.CategoryService;
import com.unimag.tiendauniversitaria.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<CustomerDtos.CustomerResponse> create(@Valid @RequestBody CustomerDtos.CustomerCreateRequest req,
                                                                UriComponentsBuilder uriBuilder) {
        var body = service.create(req);
        var location = uriBuilder.path("/api/v1/customers/{id}")
                .buildAndExpand(body.id()).toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDtos.CustomerResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<CustomerDtos.CustomerResponse>> list(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        var result = service.list(PageRequest.of(page, size, Sort.by("id").ascending()));
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDtos.CustomerResponse> update(@PathVariable Long id,
                                                                @Valid @RequestBody CustomerDtos.CustomerUpdateRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}