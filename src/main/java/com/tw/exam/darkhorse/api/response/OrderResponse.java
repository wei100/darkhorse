package com.tw.exam.darkhorse.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;

    private String price;

    private Long addressId;

    private Long goodsId;

    private String payType;

    private String status;

    private LocalDateTime createdAt;
}
