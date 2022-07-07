package com.tw.exam.darkhorse.api.assembler;

import com.tw.exam.darkhorse.api.response.OrderRefundResponse;
import com.tw.exam.darkhorse.api.response.OrderResponse;
import com.tw.exam.darkhorse.domain.model.OrderModel;
import com.tw.exam.darkhorse.domain.model.OrderRefundModel;
import com.tw.exam.darkhorse.infrastructure.repository.entity.OrderRefund;
import org.springframework.stereotype.Component;

@Component
public class OrderRefundAssembler {

    public OrderRefundResponse toResource(OrderRefundModel orderRefundModel) {
        return OrderRefundResponse.builder()
                .id(orderRefundModel.getId())
                .price(orderRefundModel.getPrice())
                .orderId(orderRefundModel.getOrderId())
                .status(orderRefundModel.getStatus())
                .build();
    }
}
