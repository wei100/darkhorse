package com.tw.exam.darkhorse.infrastructure.apigateway.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCalculateRequest {
    private Double price;
    private Long addressId;
    private Long goodsId;
}
