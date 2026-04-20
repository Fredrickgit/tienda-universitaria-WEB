package com.unimag.tiendauniversitaria.mapper;

import com.unimag.tiendauniversitaria.dto.response.MonthlyRevenueResponse;
import com.unimag.tiendauniversitaria.dto.response.TopCustomerRevenueResponse;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir resultados de queries personalizadas a DTOs de reportes
 * Maneja conversión de Object[] y datos agregados a estructuras tipadas
 */
public class ReportMapper {

    private ReportMapper() {
        // Clase utilitaria, no instanciable
    }

    /**
     * Convierte un Object[] de ingresos mensuales a MonthlyRevenueResponse
     * Espera formato: [month (YearMonth o String), totalRevenue (BigDecimal)]
     *
     * @param data arreglo con datos de mes e ingresos
     * @return MonthlyRevenueResponse con datos mapeados
     */
    public static MonthlyRevenueResponse toMonthlyRevenueResponse(Object[] data) {
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

        return MonthlyRevenueResponse.builder()
                .month(month)
                .totalRevenue(totalRevenue)
                .build();
    }

    /**
     * Convierte una lista de Object[] a lista de MonthlyRevenueResponse
     *
     * @param dataList lista de arreglos con datos mensuales
     * @return lista de MonthlyRevenueResponse mapeados
     */
    public static List<MonthlyRevenueResponse> toMonthlyRevenueList(List<Object[]> dataList) {
        if (dataList == null) {
            return null;
        }

        return dataList.stream()
                .map(ReportMapper::toMonthlyRevenueResponse)
                .collect(Collectors.toList());
    }

    /**
     * Convierte un Object[] de cliente top a TopCustomerRevenueResponse
     * Espera formato: [customerId (Long), customerName (String), totalRevenue (BigDecimal)]
     *
     * @param data arreglo con datos de cliente e ingresos
     * @return TopCustomerRevenueResponse con datos mapeados
     */
    public static TopCustomerRevenueResponse toTopCustomerRevenueResponse(Object[] data) {
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

        return TopCustomerRevenueResponse.builder()
                .customerId(customerId)
                .customerName(customerName)
                .totalRevenue(totalRevenue)
                .build();
    }

    /**
     * Convierte una lista de Object[] a lista de TopCustomerRevenueResponse
     *
     * @param dataList lista de arreglos con datos de clientes top
     * @return lista de TopCustomerRevenueResponse mapeados
     */
    public static List<TopCustomerRevenueResponse> toTopCustomerRevenueList(List<Object[]> dataList) {
        if (dataList == null) {
            return null;
        }

        return dataList.stream()
                .map(ReportMapper::toTopCustomerRevenueResponse)
                .collect(Collectors.toList());
    }
}
