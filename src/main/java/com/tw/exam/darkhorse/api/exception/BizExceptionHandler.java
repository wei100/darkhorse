package com.tw.exam.darkhorse.api.exception;

import static com.tw.exam.darkhorse.api.exception.ErrorCode.SYSTEM_ERROR;

import com.tw.exam.darkhorse.api.response.ErrorResponse;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class BizExceptionHandler {

    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity bizExceptionHandler(BusinessException e) {
        log.error("biz exception, error = {}, error message = {}", e, e.getMessage());
        return new ResponseEntity(new ErrorResponse(e), e.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity badRequestHandler(MethodArgumentNotValidException e) {
        log.error("Bad request error = {}, error message = {}", e, e.getMessage());
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        if (null != error) {
            return new ResponseEntity(
                ErrorResponse.builder()
                    .code(ErrorCode.PARAMETER_INVALID.getCode())
                    .message(ErrorCode.PARAMETER_INVALID.getMessage())
                    .field(error.getField())
                    .build(),
                HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(
            new ErrorResponse(ErrorCode.PARAMETER_INVALID), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity badRequestHandler(MissingServletRequestParameterException e) {
        log.error("Bad request error = {}, error message = {}", e, e.getMessage());
        String field = Optional.ofNullable(e.getParameterName()).orElse("");
        return new ResponseEntity(
            ErrorResponse.builder()
                .code(ErrorCode.PARAMETER_INVALID.getCode())
                .message(ErrorCode.PARAMETER_INVALID.getMessage())
                .field(field)
                .build(),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity badRequestHandler(MethodArgumentTypeMismatchException e) {
        log.error("Bad request error = {}, error message = {}", e, e.getMessage());
        String field = Optional.ofNullable(e.getName()).orElse("");
        return new ResponseEntity(
            ErrorResponse.builder()
                .code(ErrorCode.PARAMETER_INVALID.getCode())
                .message(ErrorCode.PARAMETER_INVALID.getMessage())
                .field(field)
                .build(),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity systemErrorHandler(HttpServletRequest req, Exception e) {

        log.error("error happen when call url = {}, query string = {}, error = {}",
            req.getRequestURI(),
            req.getQueryString(),
            e);

        return new ResponseEntity(
            new ErrorResponse(SYSTEM_ERROR),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
