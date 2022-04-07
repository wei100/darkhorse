package com.tw.exam.darkhorse.api.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SYSTEM_ERROR("50030", "当前服务不可用，请稍后重试"),
    PARAMETER_INVALID("40010", "参数错误"),
    ORDER_NOT_EXIST("40030", "订单不存在"),
    ADDRESS_NOT_EXIST("40020", "指定的地址不存在，请确认"),
    PAYMENT_FAILURE("50040", "支付失败，请换种支付方式");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
