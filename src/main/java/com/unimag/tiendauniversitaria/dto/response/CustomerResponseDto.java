package com.unimag.tiendauniversitaria.dto.response;

import com.unimag.tiendauniversitaria.enums.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDto {

    private Long customerId;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private CustomerStatus status;
    private LocalDateTime createdAt;
    private AddressDto address;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressDto {
        private Long addressId;
        private String street;
        private String city;
        private String department;
        private String postalCode;
        private Boolean isDefault;
    }
}
