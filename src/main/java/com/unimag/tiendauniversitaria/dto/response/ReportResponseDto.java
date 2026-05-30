package com.unimag.tiendauniversitaria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

/**
 * DTO consolidado de respuesta para operaciones de reportes
 * Unifica PeriodReportResponse, TopCustomerRevenueResponse y MonthlyRevenueResponse
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponseDto {
    
    private LocalDateTime generatedAt;
    private String reportType;
    
    // Para reportes por período
    private PeriodReportData periodData;
    
    // Para clientes principales
    private List<TopCustomerData> topCustomers;
    
    // Para ingresos mensuales
    private List<MonthlyRevenueData> monthlyRevenue;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PeriodReportData {
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Long totalOrders;
        private BigDecimal totalRevenue;
        private BigDecimal averageOrderValue;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopCustomerData {
        private Long customerId;
        private String customerName;
        private Long totalOrders;
        private BigDecimal totalSpent;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthlyRevenueData {
        private YearMonth month;
        private BigDecimal revenue;
        private Long ordersCount;
    }
}
