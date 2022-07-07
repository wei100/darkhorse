package com.tw.exam.darkhorse.service;

import com.tw.exam.darkhorse.api.exception.BusinessException;
import com.tw.exam.darkhorse.common.MockOrder;
import com.tw.exam.darkhorse.domain.service.OrderService;
import com.tw.exam.darkhorse.domain.model.OrderModel;
import com.tw.exam.darkhorse.infrastructure.apigateway.OrderApiGateway;
import com.tw.exam.darkhorse.infrastructure.apigateway.request.OrderCalculateRequest;
import com.tw.exam.darkhorse.infrastructure.apigateway.response.OrderCalculateResponse;
import com.tw.exam.darkhorse.infrastructure.repository.OrderRepository;
import com.tw.exam.darkhorse.infrastructure.repository.entity.Order;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceTest implements MockOrder {

    private final OrderRepository orderRepository = mock(OrderRepository.class);

    private final OrderApiGateway orderApiGateway = mock(OrderApiGateway.class);

    private final OrderService orderService = new OrderService(orderRepository, orderApiGateway);

    /**
     * Story1 - AC1 - Example1 - Task4
     */
    @Test
    public void should_return_order_when_save_order_succeed() {
        Order savedOrder = Order.builder().id(ORDER_ID).price(PRICE).addressId(ADDRESS_ID).goodsId(GOODS_ID).payType(PAY_TYPE).createdAt(LocalDateTime.now()).build();
        OrderModel orderModel = OrderModel.builder()
                .price(PRICE)
                .addressId(ADDRESS_ID)
                .goodsId(GOODS_ID)
                .payType(PAY_TYPE)
                .build();
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
        when(orderApiGateway.calculate(any(OrderCalculateRequest.class))).thenReturn(OrderCalculateResponse.builder()
                .price(Double.parseDouble(savedOrder.getPrice()))
                .build());
        OrderModel savedOrderModel = orderService.save(orderModel);
        Assert.assertEquals(savedOrderModel.getPrice(), PRICE);
        Assert.assertEquals(savedOrderModel.getAddressId(), ADDRESS_ID);
        Assert.assertEquals(savedOrderModel.getGoodsId(), GOODS_ID);
    }

    /**
     * Story1 - AC2 - Example1 - Task4
     */
    @Test
    public void should_throw_error_when_save_order_failed() {
        Order savedOrder = Order.builder().id(ORDER_ID).price(PRICE).addressId(ADDRESS_ID).goodsId(GOODS_ID).payType(PAY_TYPE).createdAt(LocalDateTime.now()).build();
        OrderModel orderModel = OrderModel.builder()
                .price(PRICE)
                .addressId(ADDRESS_ID)
                .goodsId(GOODS_ID)
                .payType(PAY_TYPE)
                .build();
        when(orderApiGateway.calculate(any(OrderCalculateRequest.class))).thenThrow(new RuntimeException());

        var exception = assertThrows(
                BusinessException.class,
                () -> orderService.save(orderModel),
                "订单计费出错"
        );
        assertThat(exception.getMessage()).isNotNull();
    }
}
