package com.tw.exam.darkhorse.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException {

    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    private String code;
    private String message;
    private String customMessage;

    public BusinessException(String message) {
        this.customMessage = message;
    }

    public BusinessException(ErrorCode error) {
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public BusinessException(HttpStatus httpStatus, ErrorCode error) {
        this.httpStatus = httpStatus;
        this.code = error.getCode();
        this.message = error.getMessage();
    }
}
