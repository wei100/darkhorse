package com.tw.exam.darkhorse.infrastructure.repository;

import com.tw.exam.darkhorse.DarkHorseApplication;
import com.tw.exam.darkhorse.common.MockOrder;
import com.tw.exam.darkhorse.infrastructure.repository.entity.Order;
import com.tw.exam.darkhorse.infrastructure.repository.entity.OrderRefund;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DarkHorseApplication.class)
public class OrderRefundRepositoryTest implements MockOrder {

    @Autowired
    private OrderRefundRepository orderRefundRepository;

    @BeforeEach
    void setUp() {
        orderRefundRepository.deleteAll();
    }

    /**
     * Story2 - AC1 - Example1 - Task1
     */
    @Test
    public void should_return_saved_order_refund_when_save_order_refund() {
        //given
        OrderRefund data = OrderRefund.builder()
                .price(Double.parseDouble(PRICE))
                .orderId(ORDER_ID)
                .status(BACK_STATUS)
                .createdAt(LocalDateTime.now())
                .build();

        //when
        var orderRefund = orderRefundRepository.save(data);

        //then
        assertThat(orderRefund).isNotNull();
        assertThat(orderRefund.getPrice()).isEqualTo(Double.parseDouble(PRICE));
        assertThat(orderRefund.getOrderId()).isEqualTo(ORDER_ID);
        assertThat(orderRefund.getStatus()).isEqualTo(BACK_STATUS);
        assertThat(orderRefund.getId()).isNotNull();
    }
}
