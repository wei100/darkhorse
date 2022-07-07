package com.tw.exam.darkhorse.service;

import com.tw.exam.darkhorse.api.exception.BusinessException;
import com.tw.exam.darkhorse.common.MockOrder;
import com.tw.exam.darkhorse.domain.model.OrderModel;
import com.tw.exam.darkhorse.domain.model.OrderRefundModel;
import com.tw.exam.darkhorse.domain.service.OrderRefundService;
import com.tw.exam.darkhorse.domain.service.OrderService;
import com.tw.exam.darkhorse.infrastructure.adapter.OrderRefundAdapter;
import com.tw.exam.darkhorse.infrastructure.apigateway.OrderApiGateway;
import com.tw.exam.darkhorse.infrastructure.apigateway.request.OrderCalculateRequest;
import com.tw.exam.darkhorse.infrastructure.apigateway.response.OrderCalculateResponse;
import com.tw.exam.darkhorse.infrastructure.repository.OrderRefundRepository;
import com.tw.exam.darkhorse.infrastructure.repository.OrderRepository;
import com.tw.exam.darkhorse.infrastructure.repository.entity.Order;
import com.tw.exam.darkhorse.infrastructure.repository.entity.OrderRefund;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderRefundServiceTest implements MockOrder {

    private final OrderRefundRepository orderRefundRepository = mock(OrderRefundRepository.class);

    private final OrderRefundAdapter orderRefundAdapter = mock(OrderRefundAdapter.class);

    private final OrderRepository orderRepository = mock(OrderRepository.class);

    private final OrderRefundService orderRefundService = new OrderRefundService(orderRefundRepository, orderRefundAdapter, orderRepository);

    /**
     * Story2 - AC1 - Example1 - Task4
     */
    @Test
    public void should_return_order_when_save_order_succeed() {
        OrderRefund savedOrderRefund = OrderRefund.builder().id(ORDER_ID).price(D_PRICE).orderId(ORDER_ID).status(BACK_STATUS).createdAt(LocalDateTime.now()).build();
        OrderRefundModel orderRefundModel = OrderRefundModel.builder()
                .price(D_PRICE)
                .orderId(ORDER_ID)
                .status(BACK_STATUS)
                .build();
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(Order.builder().build()));
        when(orderRefundRepository.save(any(OrderRefund.class))).thenReturn(savedOrderRefund);
        when(orderRefundAdapter.pushOrderRefund(any(OrderRefund.class))).thenReturn(Boolean.TRUE);
        var savedOrderRefundModel = orderRefundService.save(orderRefundModel);
        Assert.assertEquals(savedOrderRefundModel.getPrice(), D_PRICE);
        Assert.assertEquals(savedOrderRefundModel.getOrderId(), ORDER_ID);
        Assert.assertEquals(savedOrderRefundModel.getStatus(), BACK_STATUS);
    }

    /**
     * Story2 - AC2 - Example1 - Task4
     */
    @Test
    public void should_throw_error_when_save_order_refund_failed() {
        OrderRefund savedOrderRefund = OrderRefund.builder().price(D_PRICE).orderId(ORDER_ID).status(BACK_STATUS).createdAt(LocalDateTime.now()).build();
        OrderRefundModel orderRefundModel = OrderRefundModel.builder()
                .price(D_PRICE)
                .orderId(ORDER_ID)
                .status(BACK_STATUS)
                .build();
        when(orderRepository.findById(any(Long.class))).thenReturn(null);
        ;
        var exception = assertThrows(
                BusinessException.class,
                () -> orderRefundService.save(orderRefundModel),
                "指定的订单不存在"
        );
        assertThat(exception.getMessage()).isNotNull();
    }
}
