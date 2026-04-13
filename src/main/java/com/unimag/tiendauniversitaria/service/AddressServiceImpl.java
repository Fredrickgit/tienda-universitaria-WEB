package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.api.dto.AddressDtos;
import com.unimag.tiendauniversitaria.exception.NotFoundException;
import com.unimag.tiendauniversitaria.repository.AddressRepository;
import com.unimag.tiendauniversitaria.repository.CustomerRepository;
import com.unimag.tiendauniversitaria.service.mapper.AddressMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repo;
    private final CustomerRepository customerRepo;

    @Override
    public AddressDtos.AddressResponse create(AddressDtos.AddressCreateRequest req) {
        var customer = customerRepo.findById(req.customerId())
                .orElseThrow(() -> new NotFoundException("Customer %d not found".formatted(req.customerId())));

        var entity = AddressMapper.toEntity(req, customer);
        var saved = repo.save(entity);

        return AddressMapper.toResponse(saved);
    }

    @Override
    @Transactional//
    public AddressDtos.AddressResponse get(Long id) {
        return repo.findById(id)
                .map(AddressMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Address %d not found".formatted(id)));
    }

    @Override
    @Transactional//
    public List<AddressDtos.AddressResponse> list() {
        return repo.findAll().stream()
                .map(AddressMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}