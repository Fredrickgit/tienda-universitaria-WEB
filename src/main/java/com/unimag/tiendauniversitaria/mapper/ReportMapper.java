package com.unimag.tiendauniversitaria.mapper;

import com.unimag.tiendauniversitaria.dto.response.ReportResponseDto;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir resultados de queries personalizadas a DTOs consolidados de reportes
 * Maneja conversión de Object[] y datos agregados a estructuras tipadas
 */
public class ReportMapper {

    private ReportMapper() {
        // Clase utilitaria, no instanciable
    }

    /**
     * Convierte un Object[] de ingresos mensuales a MonthlyRevenueData
     * Espera formato: [year, month, totalRevenue, ordersCount]
     *
     * @param data arreglo con datos de mes e ingresos
     * @return MonthlyRevenueData con datos mapeados
     */
    public static ReportResponseDto.MonthlyRevenueData toMonthlyRevenueResponse(Object[] data) {
        if (data == null || data.length < 3) {
            return null;
        }

        YearMonth month = YearMonth.of(
                ((Number) data[0]).intValue(),
                ((Number) data[1]).intValue()
        );

        BigDecimal totalRevenue = data[2] instanceof BigDecimal
                ? (BigDecimal) data[2]
                : new BigDecimal(data[2].toString());
        Long ordersCount = data.length > 3 && data[3] != null
                ? ((Number) data[3]).longValue()
                : null;

        return ReportResponseDto.MonthlyRevenueData.builder()
                .month(month)
                .revenue(totalRevenue)
                .ordersCount(ordersCount)
                .build();
    }

    /**
     * Convierte una lista de Object[] a lista de MonthlyRevenueData
     *
     * @param dataList lista de arreglos con datos mensuales
     * @return lista de MonthlyRevenueData mapeados
     */
    public static List<ReportResponseDto.MonthlyRevenueData> toMonthlyRevenueList(List<Object[]> dataList) {
        if (dataList == null) {
            return null;
        }

        return dataList.stream()
                .map(ReportMapper::toMonthlyRevenueResponse)
                .collect(Collectors.toList());
    }

    /**
     * Convierte un Object[] de cliente top a TopCustomerData
     * Espera formato: [customerId, customerName, totalOrders, totalRevenue]
     *
     * @param data arreglo con datos de cliente e ingresos
     * @return TopCustomerData con datos mapeados
     */
    public static ReportResponseDto.TopCustomerData toTopCustomerRevenueResponse(Object[] data) {
        if (data == null || data.length < 4) {
            return null;
        }

        Long customerId = ((Number) data[0]).longValue();

        String customerName = data[1].toString();
        Long totalOrders = ((Number) data[2]).longValue();

        BigDecimal totalRevenue = data[3] instanceof BigDecimal
                ? (BigDecimal) data[3]
                : new BigDecimal(data[3].toString());

        return ReportResponseDto.TopCustomerData.builder()
                .customerId(customerId)
                .customerName(customerName)
                .totalOrders(totalOrders)
                .totalSpent(totalRevenue)
                .build();
    }

    /**
     * Convierte una lista de Object[] a lista de TopCustomerData
     *
     * @param dataList lista de arreglos con datos de clientes top
     * @return lista de TopCustomerData mapeados
     */
    public static List<ReportResponseDto.TopCustomerData> toTopCustomerRevenueList(List<Object[]> dataList) {
        if (dataList == null) {
            return null;
        }

        return dataList.stream()
                .map(ReportMapper::toTopCustomerRevenueResponse)
                .collect(Collectors.toList());
    }
}
