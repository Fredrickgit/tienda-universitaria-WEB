package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.api.dto.CustomerDtos;

import java.util.List;

    public interface CustomerService {
        CustomerDtos.CustomerResponse create(CustomerDtos.CustomerCreateRequest req);
        CustomerDtos.CustomerResponse get(Long id);
        List<CustomerDtos.CustomerResponse> list();
        void delete(Long id);
    }

