package com.example.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AdviceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler (CommonException.class)
    public ResponseEntity <?> handle(CommonException commonException) {
        String message = commonException.getMessage();
        return new ResponseEntity(message, HttpStatus.NOT_FOUND);
    }


}
