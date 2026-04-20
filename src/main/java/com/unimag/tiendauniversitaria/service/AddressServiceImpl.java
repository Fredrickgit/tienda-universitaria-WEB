package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.api.dto.AddressDtos;
import com.unimag.tiendauniversitaria.exception.NotFoundException;
import com.unimag.tiendauniversitaria.repository.AddressRepository;
import com.unimag.tiendauniversitaria.repository.CustomerRepository;
import com.unimag.tiendauniversitaria.service.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public AddressDtos.AddressResponse get(Long id) {
        return repo.findById(id)
                .map(AddressMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Address %d not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressDtos.AddressResponse> list() {
        return repo.findAll().stream()
                .map(AddressMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AddressDtos.AddressResponse> list(Pageable pageable) {
        return repo.findAll(pageable)
                .map(AddressMapper::toResponse);
    }

    @Override
    public AddressDtos.AddressResponse update(Long id, AddressDtos.AddressUpdateRequest req) {
        var address = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Address %d not found".formatted(id)));
        AddressMapper.updateEntity(address, req);
        var saved = repo.save(address);
        return AddressMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}