package com.tw.exam.darkhorse.infrastructure.repository.entity;

import com.tw.exam.darkhorse.domain.model.OrderModel;
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
@Table(name = "food_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String price;

    private Long addressId;

    private Long goodsId;

    private String payType;

    private String status;

    private LocalDateTime createdAt;

    public Order of(OrderModel order) {
        return Order.builder()
                .id(order.getId())
                .price(order.getPrice())
                .addressId(order.getAddressId())
                .goodsId(order.getGoodsId())
                .payType(order.getPayType())
                .status(order.getStatus())
                .build();
    }
}
