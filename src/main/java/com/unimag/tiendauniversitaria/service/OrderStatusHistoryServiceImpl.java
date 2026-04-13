package com.unimag.tiendauniversitaria.service;

import com.unimag.tiendauniversitaria.api.dto.OrderStatushistoryDtos;
import com.unimag.tiendauniversitaria.repository.OrderStatusHistoryRepository;
import com.unimag.tiendauniversitaria.service.mapper.OrderStatusHistoryMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderStatusHistoryServiceImpl implements OrderStatusHistoryService {

    private final OrderStatusHistoryRepository repo;

    @Override
    @Transactional//
    public List<OrderStatushistoryDtos.OrderStatusHistoryResponse> getByOrder(Long orderId) {
        return repo.findByOrderIdOrderByChangedAtAsc(orderId)
                //orderId
                .stream()
                .map(OrderStatusHistoryMapper::toResponse)
                .toList();
    }
}