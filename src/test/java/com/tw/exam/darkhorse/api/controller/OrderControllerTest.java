package com.tw.exam.darkhorse.api.controller;

import com.google.gson.Gson;
import com.tw.exam.darkhorse.api.request.CreateOrderRequest;
import com.tw.exam.darkhorse.common.MockOrder;
import com.tw.exam.darkhorse.domain.model.Order;
import com.tw.exam.darkhorse.domain.service.OrderService;
import com.tw.exam.darkhorse.domain.service.PaymentService;
import com.tw.exam.darkhorse.DarkHorseApplication;
import com.tw.exam.darkhorse.api.exception.BusinessException;
import com.tw.exam.darkhorse.api.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static com.tw.exam.darkhorse.api.exception.ErrorCode.ADDRESS_NOT_EXIST;
import static com.tw.exam.darkhorse.api.exception.ErrorCode.PARAMETER_INVALID;
import static com.tw.exam.darkhorse.api.exception.ErrorCode.SYSTEM_ERROR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DarkHorseApplication.class)
@AutoConfigureMockMvc
public class OrderControllerTest implements MockOrder {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private PaymentService paymentService;



    /**
     * Create Order API - AC1 - Example1 - Task3
     */
    @Test
    public void should_return_created_order_with_201_when_create_order_given_correct_order_info() throws Exception {

        CreateOrderRequest request = CreateOrderRequest.builder()
                .price(PRICE)
                .addressId(ADDRESS_ID)
                .goodsId(GOODS_ID)
                .payType(PAY_TYPE)
                .build();

        when(orderService.createOrder(request))
                .thenReturn(Order.builder().id(ORDER_ID).price(PRICE).addressId(ADDRESS_ID).goodsId(GOODS_ID).payType(PAY_TYPE).createdAt(LocalDateTime.now()).build());
        mockMvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .content(new Gson().toJson(request))
        )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(ORDER_ID))
            .andReturn();
    }

    /**
     * Create Order API - AC4 - Example1 - Task4
     */
    @Test
    public void should_return_error_response_with_400_when_create_order_given_payType_invalid() throws Exception {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .price(PRICE)
                .addressId(ADDRESS_ID)
                .goodsId(GOODS_ID)
                .payType("invalid pay type")
                .build();

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(request))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(PARAMETER_INVALID.getCode()))
                .andExpect(jsonPath("$.message").value(PARAMETER_INVALID.getMessage()))
                .andReturn();
    }

    /**
     * Create Order API - AC2 - Example1 - Task4
     */
    @Test
    public void should_return_error_response_with_400_when_create_order_given_not_exist_address_exception() throws Exception {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .price(PRICE)
                .addressId(12L)
                .goodsId(GOODS_ID)
                .payType(PAY_TYPE)
                .build();

        when(orderService.createOrder(any(CreateOrderRequest.class)))
                .thenThrow(new BusinessException(ADDRESS_NOT_EXIST));

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(request))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ADDRESS_NOT_EXIST.getCode()))
                .andExpect(jsonPath("$.message").value(ADDRESS_NOT_EXIST.getMessage()))
                .andReturn();
    }

    /**
     * Create Order API - AC3 - Example1 - Task4
     */
    @Test
    public void should_return_error_response_with_500_when_create_order_given_db_exception() throws Exception {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .price(PRICE)
                .addressId(ADDRESS_ID)
                .goodsId(GOODS_ID)
                .payType(PAY_TYPE)
                .build();

        when(orderService.createOrder(any(CreateOrderRequest.class)))
                .thenThrow(new RuntimeException());

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(request))
        )
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(SYSTEM_ERROR.getCode()))
                .andExpect(jsonPath("$.message").value(SYSTEM_ERROR.getMessage()))
                .andReturn();
    }

    /**
     * Payment Order API - AC2 - Example1 - Task3
     */
    @Test
    public void should_return_payed_order_with_201_when_pay_order_given_correct_order_info_and_pay_info() throws Exception {
        when(paymentService.payOrder(ORDER_ID))
                .thenReturn(Boolean.TRUE);
        mockMvc.perform(post("/orders/1/payment")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("20010"))
                .andExpect(jsonPath("$.message").value("支付成功"))
                .andReturn();
    }

    /**
     * Create Order API - AC3 - Example1 - Task3
     */
    @Test
    public void should_return_payed_order_with_500_when_pay_order_given_error_order_info_and_pay_info() throws Exception {

        when(paymentService.payOrder(anyLong()))
                .thenThrow(new RuntimeException());
        mockMvc.perform(post("/orders/1/payment")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(ErrorCode.SYSTEM_ERROR.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.SYSTEM_ERROR.getMessage()))
                .andReturn();
    }

    /**
     * Create Order API - AC4 - Example1 - Task3
     */
    @Test
    public void should_return_payed_order_with_500_when_pay_order_given__order_info_not_exist() throws Exception {

        when(paymentService.payOrder(anyLong()))
                .thenThrow(new BusinessException(ErrorCode.ORDER_NOT_EXIST));
        mockMvc.perform(post("/orders/1/payment")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.ORDER_NOT_EXIST.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.ORDER_NOT_EXIST.getMessage()))
                .andReturn();
    }
}
