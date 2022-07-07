package com.tw.exam.darkhorse.infrastructure.repository.entity;

import com.tw.exam.darkhorse.domain.model.OrderModel;
import com.tw.exam.darkhorse.domain.model.OrderRefundModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_refund")
public class OrderRefund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;

    private Long orderId;

    private String status;

    private LocalDateTime createdAt;

    public OrderRefund of(OrderRefundModel orderRefund) {
        return OrderRefund.builder()
                .id(orderRefund.getId())
                .orderId(orderRefund.getOrderId())
                .price(orderRefund.getPrice())
                .status(orderRefund.getStatus())
                .build();
    }
}
