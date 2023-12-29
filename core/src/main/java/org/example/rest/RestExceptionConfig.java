package org.example.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionConfig {

  @ExceptionHandler
  public ResponseEntity<Map<String, String>> handle(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {

      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(toSnakeCase(fieldName), errorMessage);
    });
    return ResponseEntity.badRequest().body(errors);
  }

  public String toSnakeCase(String camelCase) {
    return  camelCase.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
  }
}
