package com.tw.exam.darkhorse.infrastructure.adapter;

import com.tw.exam.darkhorse.DarkHorseApplication;
import com.tw.exam.darkhorse.common.MockOrder;
import com.tw.exam.darkhorse.domain.service.OrderService;
import com.tw.exam.darkhorse.infrastructure.apigateway.OrderApiGateway;
import com.tw.exam.darkhorse.infrastructure.repository.OrderRefundRepository;
import com.tw.exam.darkhorse.infrastructure.repository.entity.Order;
import com.tw.exam.darkhorse.infrastructure.repository.entity.OrderRefund;
import com.tw.exam.darkhorse.mq.MessageQueue;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = DarkHorseApplication.class)
public class OrderRefundAdapterTest implements MockOrder {

    private final OrderRefundAdapter orderRefundAdapter = mock(OrderRefundAdapter.class);;

    /**
     * Story2 - AC1 - Example1 - Task3
     */
//    @Test
    public void should_return_saved_order_refund_when_save_order_refund() {
        OrderRefund data = OrderRefund.builder()
                .price(Double.parseDouble(PRICE))
                .orderId(ORDER_ID)
                .status(BACK_STATUS)
                .createdAt(LocalDateTime.now())
                .build();

        when(MessageQueue.add(any(OrderRefund.class))).thenReturn(true);
        var result = orderRefundAdapter.pushOrderRefund(data);
        assertThat(result).isEqualTo(true);
    }
}
