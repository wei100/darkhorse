package com.tw.exam.darkhorse.api.controller;

import com.google.gson.Gson;
import com.tw.exam.darkhorse.api.exception.BusinessException;
import com.tw.exam.darkhorse.api.exception.ErrorCode;
import com.tw.exam.darkhorse.api.request.CreateOrderRequest;
import com.tw.exam.darkhorse.common.MockOrder;
import com.tw.exam.darkhorse.domain.model.OrderModel;
import com.tw.exam.darkhorse.domain.service.OrderService;
import com.tw.exam.darkhorse.DarkHorseApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
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



    /**
     * Story1 - AC1 - Example1 - Task5
     */
    @Test
    public void should_return_created_order_with_201_when_create_order_given_correct_order_info() throws Exception {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .price(PRICE)
                .addressId(ADDRESS_ID)
                .goodsId(GOODS_ID)
                .payType("alipay")
                .build();
        OrderModel orderModel = OrderModel.builder()
                .id(ORDER_ID)
                .price(PRICE)
                .addressId(ADDRESS_ID)
                .goodsId(GOODS_ID)
                .payType(PAY_TYPE)
                .build();
        when(orderService.save(any(OrderModel.class)))
                .thenReturn(orderModel);
        mockMvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .content(new Gson().toJson(request))
        )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(ORDER_ID))
            .andExpect(jsonPath("$.price").value(PRICE))
            .andExpect(jsonPath("$.addressId").value(ADDRESS_ID))
            .andExpect(jsonPath("$.goodsId").value(GOODS_ID))
            .andReturn();
    }

    /**
     * Story1 - AC2 - Example1 - Task5
     */
    @Test
    public void should_return_500_error__when_create_order_given_correct_order_info_failed() throws Exception {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .price(PRICE)
                .addressId(ADDRESS_ID)
                .goodsId(GOODS_ID)
                .payType("alipay")
                .build();
        when(orderService.save(any(OrderModel.class))).thenThrow(new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.CALCULATE_FAILURE));
        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(request))
        )
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(ErrorCode.CALCULATE_FAILURE.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.CALCULATE_FAILURE.getMessage()))
                .andReturn();
    }
}
