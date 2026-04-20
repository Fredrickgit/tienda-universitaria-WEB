package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.api.dto.CustomerDtos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

    public interface CustomerService {
        CustomerDtos.CustomerResponse create(CustomerDtos.CustomerCreateRequest req);
        CustomerDtos.CustomerResponse get(Long id);
        List<CustomerDtos.CustomerResponse> list();
        Page<CustomerDtos.CustomerResponse> list(Pageable pageable);
        CustomerDtos.CustomerResponse update(Long id, CustomerDtos.CustomerUpdateRequest req);
        void delete(Long id);
    }

