package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.api.dto.CustomerDtos;
import com.unimag.tiendauniversitaria.exception.NotFoundException;
import com.unimag.tiendauniversitaria.repository.CustomerRepository;
import com.unimag.tiendauniversitaria.service.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repo;

    @Override
    public CustomerDtos.CustomerResponse create(CustomerDtos.CustomerCreateRequest req) {
        var entity = CustomerMapper.toEntity(req);
        var saved = repo.save(entity);
        return CustomerMapper.toResponse(saved);
    }
//
    @Override
    @Transactional(readOnly = true)
    public CustomerDtos.CustomerResponse get(Long id) {
        return repo.findById(id)
                .map(CustomerMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Customer %d not found".formatted(id)));
    }
//
    @Override
    @Transactional(readOnly = true)
    public List<CustomerDtos.CustomerResponse> list() {
        return repo.findAll().stream()
                .map(CustomerMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerDtos.CustomerResponse> list(Pageable pageable) {
        return repo.findAll(pageable)
                .map(CustomerMapper::toResponse);
    }

    @Override
    public CustomerDtos.CustomerResponse update(Long id, CustomerDtos.CustomerUpdateRequest req) {
        var customer = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer %d not found".formatted(id)));
        CustomerMapper.updateEntity(customer, req);
        var saved = repo.save(customer);
        return CustomerMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}