package com.unimag.tiendauniversitaria.controller;

import com.unimag.tiendauniversitaria.dto.ApiResponse;
import com.unimag.tiendauniversitaria.dto.request.OrderFilterRequest;
import com.unimag.tiendauniversitaria.dto.request.PeriodReportRequest;
import com.unimag.tiendauniversitaria.dto.response.*;
import com.unimag.tiendauniversitaria.entity.Inventory;
import com.unimag.tiendauniversitaria.entity.Order;
import com.unimag.tiendauniversitaria.entity.Product;
import com.unimag.tiendauniversitaria.mapper.InventoryMapper;
import com.unimag.tiendauniversitaria.mapper.OrderMapper;
import com.unimag.tiendauniversitaria.mapper.ProductMapper;
import com.unimag.tiendauniversitaria.mapper.ReportMapper;
import com.unimag.tiendauniversitaria.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador REST para la generación de reportes
 * Expone los servicios de ReportService a través de endpoints HTTP
 * Proporciona análisis e información agregada sobre productos, inventarios, pedidos y clientes
 */
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    /**
     * GET /api/reports/low-stock
     * Obtiene todos los productos con stock bajo
     * Productos donde el stock disponible es menor al stock mínimo
     *
     * @return ResponseEntity con lista de InventoryDetailsResponse con stock bajo
     */
    @GetMapping("/low-stock")
    public ResponseEntity<ApiResponse<List<InventoryDetailsResponse>>> getLowStockProducts() {
        List<Inventory> inventories = reportService.getLowStockProducts();
        List<InventoryDetailsResponse> responses = InventoryMapper.toDetailsList(inventories);
        
        return ResponseEntity.ok(ApiResponse.ofSuccess(responses, "Low stock products report retrieved successfully"));
    }

    /**
     * GET /api/reports/insufficient-stock
     * Obtiene todos los productos con stock insuficiente
     * Productos que necesitan reorden urgente
     *
     * @return ResponseEntity con lista de InventoryDetailsResponse con stock insuficiente
     */
    @GetMapping("/insufficient-stock")
    public ResponseEntity<ApiResponse<List<InventoryDetailsResponse>>> getProductsWithInsufficientStock() {
        List<Inventory> inventories = reportService.getProductsWithInsufficientStock();
        List<InventoryDetailsResponse> responses = InventoryMapper.toDetailsList(inventories);
        
        return ResponseEntity.ok(ApiResponse.ofSuccess(responses, "Insufficient stock products report retrieved successfully"));
    }

    /**
     * POST /api/reports/orders/filter
     * Obtiene pedidos filtrados por múltiples criterios
     * Permite filtrar por cliente, estado, período de tiempo y rango de totales
     *
     * @param filter DTO con criterios opcionales de filtrado
     * @return ResponseEntity con lista de OrderResponse filtrados
     */
    @PostMapping("/orders/filter")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByFilters(
            @RequestBody OrderFilterRequest filter) {
        
        List<Order> orders = reportService.getOrdersByFilters(
                filter.getCustomerId(),
                filter.getStatus(),
                filter.getStartDate(),
                filter.getEndDate(),
                filter.getMinTotal(),
                filter.getMaxTotal()
        );
        
        List<OrderResponse> responses = OrderMapper.toResponseList(orders);
        return ResponseEntity.ok(ApiResponse.ofSuccess(responses, "Filtered orders report retrieved successfully"));
    }

    /**
     * POST /api/reports/top-selling-products
     * Obtiene los productos más vendidos en un período específico
     * Ranking basado en cantidad de unidades vendidas
     *
     * @param request DTO con startDate y endDate del período
     * @return ResponseEntity con lista de ProductResponse ordenados por ventas
     */
    @PostMapping("/top-selling-products")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getTopSellingProductsByPeriod(
            @Valid @RequestBody PeriodReportRequest request) {
        
        List<Product> products = reportService.getTopSellingProductsByPeriod(
                request.getStartDate(),
                request.getEndDate()
        );
        
        List<ProductResponse> responses = ProductMapper.toProductList(products);
        return ResponseEntity.ok(ApiResponse.ofSuccess(responses, "Top selling products report retrieved successfully"));
    }

    /**
     * POST /api/reports/monthly-revenue
     * Obtiene los ingresos generados por mes en un período específico
     * Permite análisis de tendencias de ingresos
     *
     * @param request DTO con startDate y endDate del período
     * @return ResponseEntity con lista de MonthlyRevenueResponse agrupados por mes
     */
    @PostMapping("/monthly-revenue")
    public ResponseEntity<ApiResponse<List<MonthlyRevenueResponse>>> getMonthlyRevenue(
            @Valid @RequestBody PeriodReportRequest request) {
        
        List<Object[]> data = reportService.getMonthlyRevenue(
                request.getStartDate(),
                request.getEndDate()
        );
        
        List<MonthlyRevenueResponse> responses = ReportMapper.toMonthlyRevenueList(data);
        return ResponseEntity.ok(ApiResponse.ofSuccess(responses, "Monthly revenue report retrieved successfully"));
    }

    /**
     * POST /api/reports/top-customers
     * Obtiene los clientes con mayor ingresos generado en un período específico
     * Útil para identificar clientes VIP y más valiosos
     *
     * @param request DTO con startDate y endDate del período
     * @return ResponseEntity con lista de TopCustomerRevenueResponse ordenados por ingresos
     */
    @PostMapping("/top-customers")
    public ResponseEntity<ApiResponse<List<TopCustomerRevenueResponse>>> getTopCustomersByRevenue(
            @Valid @RequestBody PeriodReportRequest request) {
        
        List<Object[]> data = reportService.getTopCustomersByRevenue(
                request.getStartDate(),
                request.getEndDate()
        );
        
        List<TopCustomerRevenueResponse> responses = ReportMapper.toTopCustomerRevenueList(data);
        return ResponseEntity.ok(ApiResponse.ofSuccess(responses, "Top customers report retrieved successfully"));
    }
}
