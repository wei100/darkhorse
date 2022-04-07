package com.tw.exam.darkhorse.service;

import com.tw.exam.darkhorse.api.request.CreateOrderRequest;
import com.tw.exam.darkhorse.domain.service.PaymentService;
import com.tw.exam.darkhorse.adapter.RedEnvelopePayAdapter;
import com.tw.exam.darkhorse.common.MockOrder;
import com.tw.exam.darkhorse.domain.model.RedEnvelope;
import com.tw.exam.darkhorse.domain.service.OrderService;
import com.tw.exam.darkhorse.domain.model.Order;
import com.tw.exam.darkhorse.infrastructure.repository.OrderRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceTest implements MockOrder {

    private final OrderRepository orderRepository = mock(OrderRepository.class);

    private final OrderService orderService = new OrderService(orderRepository);

    private final RedEnvelopePayAdapter redEnvelopePayAdapter = mock(RedEnvelopePayAdapter.class);

    private final PaymentService paymentService = new PaymentService(orderService, redEnvelopePayAdapter);

    /**
     * Create Order API - AC1-Example1-Task2
     */
    @Test
    public void should_return_order_when_create_order_succeed() {
        Order createdData = Order.builder().id(ORDER_ID).price(PRICE).addressId(ADDRESS_ID).goodsId(GOODS_ID).payType(PAY_TYPE).createdAt(LocalDateTime.now()).build();
        CreateOrderRequest request = CreateOrderRequest.builder()
                .price(PRICE)
                .addressId(ADDRESS_ID)
                .goodsId(GOODS_ID)
                .payType(PAY_TYPE)
                .build();
        when(orderRepository.save(any(Order.class))).thenReturn(createdData);
        Assert.assertEquals(createdData, orderService.createOrder(request));
    }

    /**
     * Payment Order API - AC1-Example1-Task2
     */
    @Test
    public void should_return_payment_when_pay_order_succeed() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(Order.builder().id(ORDER_ID).build()));
        RedEnvelope prePayInfo = RedEnvelope.builder().id(1L).price(100.10).status("unused").build();
        when(redEnvelopePayAdapter.getRedEnvelopes()).thenReturn(List.of(prePayInfo));
        when(redEnvelopePayAdapter.updateRedEnvelopes(prePayInfo)).thenReturn(RedEnvelope.builder().id(1L).price(100.10).status("used").build());
        Assert.assertTrue(paymentService.payOrder(ORDER_ID));
    }

    /**
     * Payment Order API - AC2-Example1-Task2
     */
    @Test
    public void should_return_payment_when_pay_order_succeed_with_red_pay_down() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(Order.builder().id(ORDER_ID).build()));
        RedEnvelope prePayInfo = RedEnvelope.builder().id(1L).price(100.10).status("unused").build();
        when(redEnvelopePayAdapter.getRedEnvelopes()).thenThrow(new RuntimeException());
        when(redEnvelopePayAdapter.updateRedEnvelopes(prePayInfo)).thenThrow(new RuntimeException());
        Assert.assertTrue(paymentService.payOrder(ORDER_ID));
    }
}
