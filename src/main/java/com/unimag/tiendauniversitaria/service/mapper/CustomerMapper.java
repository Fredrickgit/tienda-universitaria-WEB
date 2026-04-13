package com.unimag.tiendauniversitaria.service.mapper;

import com.unimag.tiendauniversitaria.api.dto.CustomerDtos;
import com.unimag.tiendauniversitaria.entity.Customer;

public class CustomerMapper {

    public static Customer toEntity(CustomerDtos.CustomerCreateRequest req) {
        Customer c = new Customer();
        c.setFirstName(req.name());
        c.setEmail(req.email());
        return c;
    }

    public static CustomerDtos.CustomerResponse toResponse(Customer c) {
        return new CustomerDtos.CustomerResponse(
                c.getId(),
                c.getFirstName(),
                c.getEmail()
        );
    }
}