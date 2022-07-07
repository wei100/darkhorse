package com.tw.exam.darkhorse.api.controller;

import com.tw.exam.darkhorse.api.assembler.OrderAssembler;
import com.tw.exam.darkhorse.api.request.CreateOrderRequest;
import com.tw.exam.darkhorse.api.response.OrderResponse;
import com.tw.exam.darkhorse.domain.service.OrderService;
import com.tw.exam.darkhorse.domain.model.OrderModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
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
public class OrderController {

    private final OrderService orderService;
    private final OrderAssembler orderAssembler;

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public OrderResponse createOrder(@RequestBody @Validated CreateOrderRequest request) {
        OrderModel orderModel = orderService.save(request.of());
        return orderAssembler.toResource(orderModel);
    }
}
