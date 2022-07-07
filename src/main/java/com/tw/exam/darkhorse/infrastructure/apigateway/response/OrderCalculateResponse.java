package com.tw.exam.darkhorse.infrastructure.apigateway.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCalculateResponse {
    private Double price;
}
