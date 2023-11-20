package com.taskmanagement.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GenericExceptions.class)
    public ResponseEntity<Object> genericExceptions(GenericExceptions ex) {
        ExceptionResponse exceptionResponse = ExceptionResponse
                .builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(ex.getLocalizedMessage())
                .status(false)
                .result(null)
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }


}