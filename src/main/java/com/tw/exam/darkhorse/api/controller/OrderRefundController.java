package com.tw.exam.darkhorse.api.controller;

import com.tw.exam.darkhorse.api.assembler.OrderAssembler;
import com.tw.exam.darkhorse.api.assembler.OrderRefundAssembler;
import com.tw.exam.darkhorse.api.request.CreateOrderRefundRequest;
import com.tw.exam.darkhorse.api.request.CreateOrderRequest;
import com.tw.exam.darkhorse.api.response.OrderRefundResponse;
import com.tw.exam.darkhorse.api.response.OrderResponse;
import com.tw.exam.darkhorse.domain.model.OrderModel;
import com.tw.exam.darkhorse.domain.service.OrderRefundService;
import com.tw.exam.darkhorse.infrastructure.repository.entity.OrderRefund;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Validated
public class OrderRefundController {

    private final OrderRefundService orderRefundService;
    private final OrderRefundAssembler orderRefundAssembler;

    @PostMapping(value = "{orderId}/red_envelope_return_confirm", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public OrderRefundResponse createOrder(@PathVariable Long orderId, @RequestBody @Validated CreateOrderRefundRequest request) {
        request.setOrderId(orderId);
        var orderRefundModel = orderRefundService.save(request.of());
        return orderRefundAssembler.toResource(orderRefundModel);
    }
}
