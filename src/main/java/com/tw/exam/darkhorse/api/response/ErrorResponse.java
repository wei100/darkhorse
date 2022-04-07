package com.tw.exam.darkhorse.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tw.exam.darkhorse.api.exception.BusinessException;
import com.tw.exam.darkhorse.api.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    private String code;
    private String message;
    private String field;

    public ErrorResponse(BusinessException e) {
        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public ErrorResponse(ErrorCode e) {
        this.code = e.getCode();
        this.message = e.getMessage();
    }
}
