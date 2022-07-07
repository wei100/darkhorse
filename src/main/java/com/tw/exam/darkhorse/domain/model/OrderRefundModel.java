package com.tw.exam.darkhorse.domain.model;

import com.tw.exam.darkhorse.infrastructure.repository.entity.Order;
import com.tw.exam.darkhorse.infrastructure.repository.entity.OrderRefund;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRefundModel {

    private Long id;

    private Double price;

    private Long orderId;

    private String status;

    private Long createdAt;

    public static OrderRefundModel of(OrderRefund orderRefund) {
        return OrderRefundModel.builder()
                .id(orderRefund.getId())
                .price(orderRefund.getPrice())
                .orderId(orderRefund.getOrderId())
                .status(orderRefund.getStatus())
                .build();
    }

    public OrderRefund of() {
        return OrderRefund.builder()
                .price(price)
                .orderId(orderId)
                .status(status)
                .build();
    }
}
