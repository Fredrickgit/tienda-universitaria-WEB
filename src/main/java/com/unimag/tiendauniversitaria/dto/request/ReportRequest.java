package com.unimag.tiendauniversitaria.dto.request;

import jakarta.validation.constraints.NotNull;
import com.unimag.tiendauniversitaria.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO consolidado para todas las operaciones de reportes
 * Maneja: reportes por período, clientes principales, ingresos mensuales, filtrado de órdenes
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequest {

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    private LocalDateTime endDate;

    // Filtros opcionales para órdenes
    private Long customerId;
    private OrderStatus status;
    private BigDecimal minTotal;
    private BigDecimal maxTotal;

    private Integer limit;

    /**
     * Tipo de reporte: PERIOD, TOP_CUSTOMERS, MONTHLY_REVENUE
     */
    @NotNull(message = "Report type is required")
    private ReportType reportType;

    public enum ReportType {
        PERIOD,
        TOP_CUSTOMERS,
        MONTHLY_REVENUE
    }
}
