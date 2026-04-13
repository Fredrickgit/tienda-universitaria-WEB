package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.api.dto.AddressDtos;

import java.util.List;

public interface AddressService {
    AddressDtos.AddressResponse create(AddressDtos.AddressCreateRequest req);
    AddressDtos.AddressResponse get(Long id);
    List<AddressDtos.AddressResponse> list();
    void delete(Long id);
}