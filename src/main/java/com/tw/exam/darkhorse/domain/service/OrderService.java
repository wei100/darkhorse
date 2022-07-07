package com.tw.exam.darkhorse.domain.service;


import com.tw.exam.darkhorse.api.exception.BusinessException;
import com.tw.exam.darkhorse.api.exception.ErrorCode;
import com.tw.exam.darkhorse.domain.model.OrderModel;
import com.tw.exam.darkhorse.infrastructure.apigateway.OrderApiGateway;
import com.tw.exam.darkhorse.infrastructure.apigateway.request.OrderCalculateRequest;
import com.tw.exam.darkhorse.infrastructure.apigateway.response.OrderCalculateResponse;
import com.tw.exam.darkhorse.infrastructure.repository.OrderRepository;
import com.tw.exam.darkhorse.infrastructure.repository.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderApiGateway orderApiGateway;

    public OrderModel save(OrderModel orderModel) {
        Order order = null;
        try {
            OrderCalculateResponse response = orderApiGateway.calculate(OrderCalculateRequest.builder()
                    .price(Double.parseDouble(orderModel.getPrice()))
                    .addressId(orderModel.getAddressId())
                    .goodsId(orderModel.getGoodsId())
                    .build()
            );
            if (!response.getPrice().equals(Double.parseDouble(orderModel.getPrice()))) {
                throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.ORDER_PRICE_CHANGE_FAILURE);
            }
            order = orderRepository.save(orderModel.of());
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.CALCULATE_FAILURE);
        }
        return OrderModel.of(order);
    }
}
