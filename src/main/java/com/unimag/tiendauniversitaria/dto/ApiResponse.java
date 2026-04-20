package com.unimag.tiendauniversitaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Wrapper genérico para respuestas API consistentes
 * Proporciona un formato estándar para éxito y error
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private Boolean success;
    private String message;
    private T data;

    /**
     * Crea una respuesta exitosa
     */
    public static <T> ApiResponse<T> ofSuccess(T data, String message) {
        return new ApiResponse<>(true, message, data);
    }

    /**
     * Crea una respuesta exitosa sin mensaje
     */
    public static <T> ApiResponse<T> ofSuccess(T data) {
        return new ApiResponse<>(true, "Success", data);
    }

    /**
     * Crea una respuesta de error
     */
    public static <T> ApiResponse<T> ofError(String message) {
        return new ApiResponse<>(false, message, null);
    }

    /**
     * Crea una respuesta de error con datos adicionales
     */
    public static <T> ApiResponse<T> ofError(String message, T data) {
        return new ApiResponse<>(false, message, data);
    }
}
