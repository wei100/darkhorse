package com.tw.exam.darkhorse.api.assembler;

import com.tw.exam.darkhorse.api.response.OrderResponse;
import com.tw.exam.darkhorse.domain.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderAssembler {

    public OrderResponse toResource(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .price(order.getPrice())
                .addressId(order.getAddressId())
                .goodsId(order.getGoodsId())
                .payType(order.getPayType())
                .status(order.getStatus())
                .build();
    }
}
