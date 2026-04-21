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
     * Espera formato: [month (YearMonth o String), totalRevenue (BigDecimal)]
     *
     * @param data arreglo con datos de mes e ingresos
     * @return MonthlyRevenueData con datos mapeados
     */
    public static ReportResponseDto.MonthlyRevenueData toMonthlyRevenueResponse(Object[] data) {
        if (data == null || data.length < 2) {
            return null;
        }

        YearMonth month;
        if (data[0] instanceof YearMonth) {
            month = (YearMonth) data[0];
        } else if (data[0] instanceof String) {
            month = YearMonth.parse((String) data[0]);
        } else {
            return null;
        }

        BigDecimal totalRevenue = data[1] instanceof BigDecimal 
                ? (BigDecimal) data[1] 
                : new BigDecimal(data[1].toString());

        return ReportResponseDto.MonthlyRevenueData.builder()
                .month(month)
                .revenue(totalRevenue)
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
     * Espera formato: [customerId (Long), customerName (String), totalRevenue (BigDecimal)]
     *
     * @param data arreglo con datos de cliente e ingresos
     * @return TopCustomerData con datos mapeados
     */
    public static ReportResponseDto.TopCustomerData toTopCustomerRevenueResponse(Object[] data) {
        if (data == null || data.length < 3) {
            return null;
        }

        Long customerId = data[0] instanceof Long 
                ? (Long) data[0] 
                : Long.parseLong(data[0].toString());

        String customerName = data[1].toString();

        BigDecimal totalRevenue = data[2] instanceof BigDecimal 
                ? (BigDecimal) data[2] 
                : new BigDecimal(data[2].toString());

        return ReportResponseDto.TopCustomerData.builder()
                .customerId(customerId)
                .customerName(customerName)
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
