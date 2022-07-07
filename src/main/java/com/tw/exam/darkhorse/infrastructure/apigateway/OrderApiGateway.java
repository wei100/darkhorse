package com.tw.exam.darkhorse.infrastructure.apigateway;

import com.tw.exam.darkhorse.config.FeignClientConfig;
import com.tw.exam.darkhorse.infrastructure.apigateway.request.OrderCalculateRequest;
import com.tw.exam.darkhorse.infrastructure.apigateway.response.OrderCalculateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "accountManageFeignClient", url = "${account-management.url}", configuration = FeignClientConfig.class)
public interface OrderApiGateway {

    @PostMapping(value = "/calculate", produces = MediaType.APPLICATION_JSON_VALUE)
    OrderCalculateResponse calculate(@RequestBody OrderCalculateRequest request);

    @PostMapping(value = "/calculate1", produces = MediaType.APPLICATION_JSON_VALUE)
    OrderCalculateResponse calculate1(@RequestBody OrderCalculateRequest request);

    @PostMapping(value = "/calculate2", produces = MediaType.APPLICATION_JSON_VALUE)
    OrderCalculateResponse calculate2(@RequestBody OrderCalculateRequest request);
}
