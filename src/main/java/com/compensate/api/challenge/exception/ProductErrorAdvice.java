package com.compensate.api.challenge.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductErrorAdvice {

  @ExceptionHandler({UpdateProductException.class})
  public ResponseEntity<String> handleUpdateProductException(UpdateProductException e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((e.getMessage()));
  }

  @ExceptionHandler({ProductNotFoundException.class})
  public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body((e.getMessage()));
  }

  @ExceptionHandler({InvalidIdException.class})
  public ResponseEntity<String> handleInvalidIdException(InvalidIdException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((e.getMessage()));
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }
}
