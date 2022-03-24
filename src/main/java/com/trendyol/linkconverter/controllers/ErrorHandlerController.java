package com.trendyol.linkconverter.controllers;

import com.trendyol.linkconverter.dto.ErrorData;
import com.trendyol.linkconverter.types.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller intended for error intercept and convert to readable kind.
 */
@Slf4j
@RestControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorData> handleValidationException(MethodArgumentNotValidException ex) {
        log.error("processMethodArgumentNotValidException: exception {}", ex.getMessage(), ex);
        return ex.getBindingResult().getAllErrors().stream()
                .map(err -> ErrorData.of(err.getDefaultMessage(), ErrorType.VALIDATION_ERROR))
                .collect(Collectors.toList());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorData handleApplicationException(Exception ex, HandlerMethod hm) {
        log.error("handleAllException: exception {}, method {}",
                ex.getMessage(), hm.getMethod().getName(), ex);
        return ErrorData.of(ex.getMessage(), ErrorType.APPLICATION_ERROR);
    }
}
