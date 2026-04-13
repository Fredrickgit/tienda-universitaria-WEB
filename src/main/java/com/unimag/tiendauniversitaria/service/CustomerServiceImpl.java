package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.api.dto.CustomerDtos;
import com.unimag.tiendauniversitaria.exception.NotFoundException;
import com.unimag.tiendauniversitaria.repository.CustomerRepository;
import com.unimag.tiendauniversitaria.service.mapper.CustomerMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    @Transactional
    public CustomerDtos.CustomerResponse get(Long id) {
        return repo.findById(id)
                .map(CustomerMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Customer %d not found".formatted(id)));
    }
//
    @Override
    @Transactional
    public List<CustomerDtos.CustomerResponse> list() {
        return repo.findAll().stream()
                .map(CustomerMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}