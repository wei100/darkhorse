package com.tw.exam.darkhorse.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RedEnvelope {

    private Long id;

    private Double price;

    private String name;

    private String status;

    private Long createdAt;
}
