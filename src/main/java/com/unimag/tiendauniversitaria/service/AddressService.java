package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.api.dto.AddressDtos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressService {
    AddressDtos.AddressResponse create(AddressDtos.AddressCreateRequest req);
    AddressDtos.AddressResponse get(Long id);
    List<AddressDtos.AddressResponse> list();
    Page<AddressDtos.AddressResponse> list(Pageable pageable);
    AddressDtos.AddressResponse update(Long id, AddressDtos.AddressUpdateRequest req);
    void delete(Long id);
}