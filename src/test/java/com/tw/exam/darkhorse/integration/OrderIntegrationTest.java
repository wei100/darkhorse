package com.tw.exam.darkhorse.integration;

import com.google.gson.Gson;
import com.tw.exam.darkhorse.DarkHorseApplication;
import com.tw.exam.darkhorse.api.exception.ErrorCode;
import com.tw.exam.darkhorse.api.request.CreateOrderRequest;
import com.tw.exam.darkhorse.common.MockOrder;
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
public class OrderIntegrationTest implements MockOrder {

    @Autowired
    protected MockMvc mockMvc;

    /**
     * Story1 - AC1 - Example1 - Task6
     */
    @Test
    public void should_return_created_order_with_201_when_create_order_given_correct_order_info() throws Exception {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .price(PRICE)
                .addressId(ADDRESS_ID)
                .goodsId(GOODS_ID)
                .payType("alipay")
                .build();

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new Gson().toJson(request))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.price").value(PRICE))
                .andExpect(jsonPath("$.addressId").value(ADDRESS_ID))
                .andExpect(jsonPath("$.goodsId").value(GOODS_ID))
                .andReturn();
    }

    /**
     * Story1 - AC2 - Example1 - Task6
     */
    @Test
    public void should_return_500_error_when_create_order_failed() throws Exception {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .price("-25")
                .addressId(ADDRESS_ID)
                .goodsId(GOODS_ID)
                .payType("alipay")
                .build();

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

    /**
     * Story1 - AC4 - Example1 - Task6
     */
    @Test
    public void should_return_400_error_when_given_a_invalid_pay_type() throws Exception {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .price("25")
                .addressId(ADDRESS_ID)
                .goodsId(GOODS_ID)
                .payType("paypal")
                .build();

        mockMvc.perform(post("/orders")
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
