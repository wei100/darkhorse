package com.tw.exam.darkhorse.integration;

import com.google.gson.Gson;
import com.tw.exam.darkhorse.DarkHorseApplication;
import com.tw.exam.darkhorse.api.exception.ErrorCode;
import com.tw.exam.darkhorse.api.request.CreateOrderRefundRequest;
import com.tw.exam.darkhorse.api.request.CreateOrderRequest;
import com.tw.exam.darkhorse.common.MockOrder;
import com.tw.exam.darkhorse.infrastructure.repository.OrderRepository;
import com.tw.exam.darkhorse.infrastructure.repository.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DarkHorseApplication.class)
@AutoConfigureMockMvc
public class OrderRefundIntegrationTest implements MockOrder {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
    }

    /**
     * Story2 - AC1 - Example1 - Task6
     */
    @Test
    public void should_return_created_order_refund_with_201_when_create_order_refund_given_correct_order_refund_info() throws Exception {
        orderRepository.save(Order.builder().price(PRICE).goodsId(GOODS_ID).addressId(ADDRESS_ID).payType(PAY_TYPE).build());
        CreateOrderRefundRequest request = CreateOrderRefundRequest.builder()
                .price(D_PRICE)
                .orderId(ORDER_ID)
                .build();

        mockMvc.perform(post("/orders/1/red_envelope_return_confirm")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(request))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.price").value(D_PRICE))
                .andExpect(jsonPath("$.orderId").value(ORDER_ID))
                .andExpect(jsonPath("$.status").value(BACK_STATUS))
                .andReturn();
    }

    /**
     * Story2 - AC2 - Example1 - Task6
     */
    @Test
    public void should_return_500_error_when_create_order_refund_failed() throws Exception {
        CreateOrderRefundRequest request = CreateOrderRefundRequest.builder()
                .price(D_PRICE)
                .orderId(ORDER_ID)
                .build();

        mockMvc.perform(post("/orders/1/red_envelope_return_confirm")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(request))
        )
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(ErrorCode.ORDER_NOT_EXIST.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.ORDER_NOT_EXIST.getMessage()))
                .andReturn();
    }

    /**
     * Story2 - AC3 - Example1 - Task6
     */
    @Test
    public void should_return_500_error_when_create_order_refund_with_invalid_price() throws Exception {
        CreateOrderRefundRequest request = CreateOrderRefundRequest.builder()
                .price(0.0)
                .orderId(ORDER_ID)
                .build();

        mockMvc.perform(post("/orders/1/red_envelope_return_confirm")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(request))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.PARAMETER_INVALID.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.PARAMETER_INVALID.getMessage()))
                .andReturn();
    }
}
