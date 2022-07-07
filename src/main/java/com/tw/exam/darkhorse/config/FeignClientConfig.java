package com.tw.exam.darkhorse.config;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

@RequiredArgsConstructor
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate ->
                requestTemplate.header("x-api-key", "PMAK-62a2eefd2c2d1d049f981c68-126b8e1936b097fc316d8a3248f9539349");
    }
}
