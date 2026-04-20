package com.unimag.tiendauniversitaria.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO de solicitud para obtener reportes por período
 * Define el rango de fechas para filtrar datos
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeriodReportRequest {
    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    private LocalDateTime endDate;
}
