package com.unimag.tiendauniversitaria.controller;

import com.unimag.tiendauniversitaria.dto.ApiResponse;
import com.unimag.tiendauniversitaria.dto.request.CustomerRequest;
import com.unimag.tiendauniversitaria.dto.response.CustomerResponseDto;
import com.unimag.tiendauniversitaria.entity.Address;
import com.unimag.tiendauniversitaria.entity.Customer;
import com.unimag.tiendauniversitaria.enums.CustomerStatus;
import com.unimag.tiendauniversitaria.repository.AddressRepository;
import com.unimag.tiendauniversitaria.repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerResponseDto>>> getAllCustomers() {
        List<CustomerResponseDto> customers = customerRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(ApiResponse.ofSuccess(customers, "Customers retrieved successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> getCustomerById(@PathVariable Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer with ID " + id + " not found"));

        return ResponseEntity.ok(ApiResponse.ofSuccess(toResponse(customer), "Customer found"));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ApiResponse<CustomerResponseDto>> createCustomer(
            @Valid @RequestBody CustomerRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Customer with email '" + request.getEmail() + "' already exists");
        }

        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .status(request.getStatus() != null ? request.getStatus() : CustomerStatus.ACTIVE)
                .build();

        Customer savedCustomer = customerRepository.save(customer);
        upsertAddress(savedCustomer, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ofSuccess(toResponse(savedCustomer), "Customer created successfully"));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ApiResponse<CustomerResponseDto>> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer with ID " + id + " not found"));

        customerRepository.findByEmail(request.getEmail())
                .filter(existingCustomer -> !existingCustomer.getId().equals(id))
                .ifPresent(existingCustomer -> {
                    throw new IllegalArgumentException("Customer with email '" + request.getEmail() + "' already exists");
                });

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setStatus(request.getStatus() != null ? request.getStatus() : customer.getStatus());
        upsertAddress(customer, request);

        return ResponseEntity.ok(ApiResponse.ofSuccess(
                toResponse(customerRepository.save(customer)),
                "Customer updated successfully"
        ));
    }

    @PatchMapping("/{id}/status")
    @Transactional
    public ResponseEntity<ApiResponse<CustomerResponseDto>> setCustomerStatus(
            @PathVariable Long id,
            @RequestParam CustomerStatus status) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer with ID " + id + " not found"));

        customer.setStatus(status);
        return ResponseEntity.ok(ApiResponse.ofSuccess(
                toResponse(customerRepository.save(customer)),
                "Customer status updated successfully"
        ));
    }

    private CustomerResponseDto toResponse(Customer customer) {
        CustomerResponseDto.AddressDto address = addressRepository.findByCustomerId(customer.getId())
                .stream()
                .filter(item -> Boolean.TRUE.equals(item.getIsDefault()))
                .findFirst()
                .or(() -> addressRepository.findByCustomerId(customer.getId()).stream().findFirst())
                .map(this::toAddressResponse)
                .orElse(null);

        return CustomerResponseDto.builder()
                .customerId(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .fullName(customer.getFirstName() + " " + customer.getLastName())
                .email(customer.getEmail())
                .status(customer.getStatus())
                .createdAt(customer.getCreatedAt())
                .address(address)
                .build();
    }

    private CustomerResponseDto.AddressDto toAddressResponse(Address address) {
        return CustomerResponseDto.AddressDto.builder()
                .addressId(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .department(address.getDepartment())
                .postalCode(address.getPostalCode())
                .isDefault(address.getIsDefault())
                .build();
    }

    private void upsertAddress(Customer customer, CustomerRequest request) {
        if (!hasAddressData(request)) {
            return;
        }

        Address address = resolveAddress(customer, request.getAddressId());
        address.setCustomer(customer);
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setDepartment(request.getDepartment());
        address.setPostalCode(request.getPostalCode());
        address.setIsDefault(true);
        addressRepository.save(address);
    }

    private Address resolveAddress(Customer customer, Long addressId) {
        if (addressId != null) {
            return addressRepository.findByIdAndCustomerId(addressId, customer.getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Address with ID " + addressId + " does not belong to customer " + customer.getId()
                    ));
        }

        return addressRepository.findByCustomerId(customer.getId())
                .stream()
                .filter(item -> Boolean.TRUE.equals(item.getIsDefault()))
                .findFirst()
                .orElseGet(() -> Address.builder().build());
    }

    private boolean hasAddressData(CustomerRequest request) {
        return request.getAddressId() != null ||
                request.getStreet() != null ||
                request.getCity() != null ||
                request.getDepartment() != null ||
                request.getPostalCode() != null;
    }
}
