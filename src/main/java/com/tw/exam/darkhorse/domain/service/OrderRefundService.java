package com.tw.exam.darkhorse.domain.service;


import com.tw.exam.darkhorse.api.exception.BusinessException;
import com.tw.exam.darkhorse.api.exception.ErrorCode;
import com.tw.exam.darkhorse.domain.model.OrderModel;
import com.tw.exam.darkhorse.domain.model.OrderRefundModel;
import com.tw.exam.darkhorse.infrastructure.adapter.OrderRefundAdapter;
import com.tw.exam.darkhorse.infrastructure.apigateway.OrderApiGateway;
import com.tw.exam.darkhorse.infrastructure.apigateway.request.OrderCalculateRequest;
import com.tw.exam.darkhorse.infrastructure.apigateway.response.OrderCalculateResponse;
import com.tw.exam.darkhorse.infrastructure.repository.OrderRefundRepository;
import com.tw.exam.darkhorse.infrastructure.repository.OrderRepository;
import com.tw.exam.darkhorse.infrastructure.repository.entity.Order;
import com.tw.exam.darkhorse.infrastructure.repository.entity.OrderRefund;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderRefundService {

    private final OrderRefundRepository orderRefundRepository;

    private final OrderRefundAdapter orderRefundAdapter;

    private final OrderRepository orderRepository;

    public OrderRefundModel save(OrderRefundModel orderRefundModel) {
        OrderRefund orderRefund = null;
        try {
            orderRepository.findById(orderRefundModel.getOrderId()).orElseThrow(() -> new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.ORDER_NOT_EXIST));

            orderRefund = orderRefundRepository.save(orderRefundModel.of());
            if (orderRefund != null) {
                boolean isSuccess = orderRefundAdapter.pushOrderRefund(orderRefund);
                if (!isSuccess) {
                    throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.ORDER_REFUND_MQ_FAILURE);
                }
            }
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.ORDER_NOT_EXIST);
        }
        return OrderRefundModel.of(orderRefund);
    }
}
