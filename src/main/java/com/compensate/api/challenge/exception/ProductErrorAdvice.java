package com.compensate.api.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductErrorAdvice {

    @ExceptionHandler({UpdateProductException.class})
    public ResponseEntity<String> handleUpdateProductException(UpdateProductException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((e.getMessage()));
    }
}
