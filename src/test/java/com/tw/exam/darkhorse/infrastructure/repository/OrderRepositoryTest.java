package com.tw.exam.darkhorse.infrastructure.repository;

import com.tw.exam.darkhorse.common.MockOrder;
import com.tw.exam.darkhorse.DarkHorseApplication;
import com.tw.exam.darkhorse.infrastructure.repository.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DarkHorseApplication.class)
public class OrderRepositoryTest implements MockOrder {

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
    }

    /**
     * Story1 - AC1 - Example1 - Task1
     */
    @Test
    public void should_return_saved_order_when_save_order() {
        //given
        Order data = Order.builder()
            .price(PRICE)
                .addressId(ADDRESS_ID)
                .goodsId(GOODS_ID)
                .payType(PAY_TYPE)
                .status(STATUS)
            .createdAt(LocalDateTime.now())
            .build();

        //when
        Order order = orderRepository.save(data);

        //then
        assertThat(order).isNotNull();
        assertThat(order.getPrice()).isEqualTo(PRICE);
        assertThat(order.getId()).isNotNull();
    }
}
