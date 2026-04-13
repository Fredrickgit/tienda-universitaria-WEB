package com.unimag.tiendauniversitaria.service.mapper;

import com.unimag.tiendauniversitaria.api.dto.AddressDtos;
import com.unimag.tiendauniversitaria.entity.Address;
import com.unimag.tiendauniversitaria.entity.Customer;

public class AddressMapper {

    public static Address toEntity(AddressDtos.AddressCreateRequest req, Customer customer) {
        Address a = new Address();
        a.setStreet(req.street());
        a.setCity(req.city());
        a.setDepartment(req.department());
        a.setCustomer(customer);
        return a;
    }

    public static AddressDtos.AddressResponse toResponse(Address a) {
        return new AddressDtos.AddressResponse(
                a.getId(),
                a.getStreet(),
                a.getCity(),
                a.getDepartment(),
                a.getCustomer().getId()
        );
    }
}