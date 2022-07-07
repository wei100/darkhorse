package com.tw.exam.darkhorse.infrastructure.apigateway;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.tw.exam.darkhorse.common.MockOrder;
import com.tw.exam.darkhorse.infrastructure.apigateway.request.OrderCalculateRequest;
import com.tw.exam.darkhorse.infrastructure.apigateway.response.OrderCalculateResponse;
import feign.FeignException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest( classes = OrderApiGatewayTest.FeignConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderApiGatewayTest implements MockOrder {

    public static WireMockServer wireMockServer;

    @Autowired
    private OrderApiGateway orderApiGateway;

    @BeforeAll
    public void setup(){
        wireMockServer = new WireMockServer(options().port(8090));
        wireMockServer.start();
    }

    /**
     * Story1 - AC1 - Example1 - Task2
     */
    @Test
    public void should_return_price_when_invoke_calculate_api() {
        //given
        OrderCalculateRequest request = OrderCalculateRequest.builder()
                .price(Double.parseDouble(PRICE))
                .addressId(ADDRESS_ID)
                .goodsId(GOODS_ID)
                .build();
        wireMockServer.stubFor(WireMock.get ( urlPathMatching("/calculate"))
                .willReturn(aResponse().withStatus(200).withBody("{\"price\": 25}")));

        //when
        OrderCalculateResponse response = orderApiGateway.calculate(request);

        //then
        assertThat(response.getPrice()).isEqualTo(Double.parseDouble(PRICE));
    }

    /**
     * Story1 - AC2 - Example1 - Task2
     */
    @Test
    public void should_throw_error_when_invoke_calculate_api_return_500() {
        //given
        OrderCalculateRequest request = OrderCalculateRequest.builder()
                .price(-25.0)
                .addressId(ADDRESS_ID)
                .goodsId(GOODS_ID)
                .build();

        // when
        wireMockServer.stubFor(WireMock.get ( urlPathMatching("/calculate1"))
                .willReturn(aResponse().withStatus(500)));

        //then
        var exception = assertThrows(
                FeignException.InternalServerError.class,
                () -> orderApiGateway.calculate1(request)
        );
        assertThat(exception.getMessage()).contains("");
    }

    /**
     * Story1 - AC3 - Example1 - Task2
     */
    @Test
    public void should_throw_error_when_invoke_calculate_api_return_504() {
        //given
        OrderCalculateRequest request = OrderCalculateRequest.builder()
                .price(Double.parseDouble(PRICE))
                .addressId(ADDRESS_ID)
                .goodsId(GOODS_ID)
                .build();

        // when
        wireMockServer.stubFor(WireMock.get ( urlPathMatching("/calculate2"))
                .willReturn(aResponse().withStatus(504).withBody("{\"price\": -25}")));

        //then
        var exception = assertThrows(
                FeignException.GatewayTimeout.class,
                () -> orderApiGateway.calculate2(request)
        );
        assertThat(exception.getMessage()).contains("");
    }

    @EnableFeignClients(clients = OrderApiGateway.class)
    @Configuration
    @EnableAutoConfiguration
    static class FeignConfig{

    }
}
