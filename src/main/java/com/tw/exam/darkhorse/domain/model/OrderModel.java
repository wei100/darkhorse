package com.tw.exam.darkhorse.domain.model;

import com.tw.exam.darkhorse.infrastructure.repository.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ui.Model;

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
public class OrderModel {

    private Long id;

    private String price;

    private Long addressId;

    private Long goodsId;

    private String payType;

    private String status;

    private LocalDateTime createdAt;

    public static OrderModel of(Order order) {
        return OrderModel.builder()
                .id(order.getId())
                .price(order.getPrice())
                .addressId(order.getAddressId())
                .goodsId(order.getGoodsId())
                .payType(order.getPayType())
                .status(order.getStatus())
                .build();
    }

    public Order of() {
        return Order.builder()
                .price(price)
                .addressId(addressId)
                .goodsId(goodsId)
                .build();
    }
}
