package com.unimag.tiendauniversitaria.api.dto;

import jakarta.validation.constraints.*;

public class AddressDtos {
    public record AddressCreateRequest(
            @NotBlank String street,
            @NotBlank String city,
            @NotBlank String country,
            @NotNull Long customerId
    ) {}

    public record AddressUpdateRequest(
            @NotBlank String street,
            @NotBlank String city,
            @NotBlank String country
    ) {}

    public record AddressResponse(
            Long id,
            String street,
            String city,
            String country,
            Long customerId
    ) {}
}
