package com.tw.exam.darkhorse.domain.service;


import com.tw.exam.darkhorse.api.request.CreateOrderRequest;
import com.tw.exam.darkhorse.domain.model.Order;
import com.tw.exam.darkhorse.infrastructure.repository.OrderRepository;
import com.tw.exam.darkhorse.api.exception.BusinessException;
import com.tw.exam.darkhorse.api.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final List<Long> addressIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L);

    private final OrderRepository orderRepository;

    public Order createOrder(CreateOrderRequest request) {
        if(addressIds.contains(request.getAddressId())) {
            return orderRepository
                    .save(Order.builder()
                            .price(request.getPrice())
                            .addressId(request.getAddressId())
                            .goodsId(request.getGoodsId())
                            .payType(request.getPayType())
                            .status("NO_PAY")
                            .createdAt(LocalDateTime.now())
                            .build());
        }
        throw new BusinessException(ErrorCode.ADDRESS_NOT_EXIST);
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_EXIST));
    }
}
