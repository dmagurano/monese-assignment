package com.monese.assignment.controller;

import com.monese.assignment.exception.InsufficientFundsException;
import com.monese.assignment.exception.UnknownAccountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UnknownAccountException.class, InsufficientFundsException.class, IllegalArgumentException.class})
    protected ResponseEntity handleInvalidRequests(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity handleGenericException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
