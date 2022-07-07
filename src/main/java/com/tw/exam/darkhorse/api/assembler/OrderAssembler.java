package com.tw.exam.darkhorse.api.assembler;

import com.tw.exam.darkhorse.api.response.OrderResponse;
import com.tw.exam.darkhorse.domain.model.OrderModel;
import org.springframework.stereotype.Component;

@Component
public class OrderAssembler {

    public OrderResponse toResource(OrderModel orderModel) {
        return OrderResponse.builder()
                .id(orderModel.getId())
                .price(orderModel.getPrice())
                .addressId(orderModel.getAddressId())
                .goodsId(orderModel.getGoodsId())
                .payType(orderModel.getPayType())
                .status(orderModel.getStatus())
                .build();
    }
}
