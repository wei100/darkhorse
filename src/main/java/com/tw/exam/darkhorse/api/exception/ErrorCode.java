package com.tw.exam.darkhorse.api.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SYSTEM_ERROR("50030", "当前服务不可用，请稍后重试"),
    PARAMETER_INVALID("40010", "请求参数错误"),
    CALCULATE_FAILURE("50010", "订单计费出错"),
    ORDER_PRICE_CHANGE_FAILURE("50020", "价格有变动，请重新支付"),
    ORDER_REFUND_MQ_FAILURE("50050", "申请红包退回失败"),
    ORDER_NOT_EXIST("50010", "系统错误，当前订单不存在");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
