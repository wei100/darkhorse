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
public class OrderRefundResponse {

    private Long id;

    private Double price;

    private Long orderId;

    private String status;

    private LocalDateTime createdAt;
}
