package com.tw.exam.darkhorse.api.controller;

import com.google.gson.Gson;
import com.tw.exam.darkhorse.DarkHorseApplication;
import com.tw.exam.darkhorse.api.exception.BusinessException;
import com.tw.exam.darkhorse.api.exception.ErrorCode;
import com.tw.exam.darkhorse.api.request.CreateOrderRefundRequest;
import com.tw.exam.darkhorse.api.request.CreateOrderRequest;
import com.tw.exam.darkhorse.common.MockOrder;
import com.tw.exam.darkhorse.domain.model.OrderModel;
import com.tw.exam.darkhorse.domain.model.OrderRefundModel;
import com.tw.exam.darkhorse.domain.service.OrderRefundService;
import com.tw.exam.darkhorse.infrastructure.repository.entity.OrderRefund;
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
public class OrderRefundControllerTest implements MockOrder {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private OrderRefundService orderRefundService;



    /**
     * Story2 - AC1 - Example1 - Task5
     */
    @Test
    public void should_return_created_order_refund_with_201_when_create_order_refund_given_correct_order_info() throws Exception {
        CreateOrderRefundRequest request = CreateOrderRefundRequest.builder()
                .price(D_PRICE)
                .orderId(ORDER_ID)
                .build();
        OrderRefundModel orderRefundModel = OrderRefundModel.builder()
                .id(ORDER_ID)
                .price(D_PRICE)
                .orderId(ORDER_ID)
                .status(BACK_STATUS)
                .build();
        when(orderRefundService.save(any(OrderRefundModel.class)))
                .thenReturn(orderRefundModel);
        mockMvc.perform(post("/orders/1/red_envelope_return_confirm")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .content(new Gson().toJson(request))
        )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(ORDER_ID))
            .andExpect(jsonPath("$.price").value(D_PRICE))
            .andExpect(jsonPath("$.orderId").value(ORDER_ID))
            .andExpect(jsonPath("$.status").value(BACK_STATUS))
            .andReturn();
    }

    /**
     * Story1 - AC2 - Example1 - Task5
     */
//    @Test
//    public void should_return_500_error__when_create_order_given_correct_order_info_failed() throws Exception {
//        CreateOrderRequest request = CreateOrderRequest.builder()
//                .price(PRICE)
//                .addressId(ADDRESS_ID)
//                .goodsId(GOODS_ID)
//                .payType("alipay")
//                .build();
//        when(orderRefundService.save(any(OrderModel.class))).thenThrow(new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.CALCULATE_FAILURE));
//        mockMvc.perform(post("/orders")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .content(new Gson().toJson(request))
//        )
//                .andExpect(status().isInternalServerError())
//                .andExpect(jsonPath("$.code").value(ErrorCode.CALCULATE_FAILURE.getCode()))
//                .andExpect(jsonPath("$.message").value(ErrorCode.CALCULATE_FAILURE.getMessage()))
//                .andReturn();
//    }
}
