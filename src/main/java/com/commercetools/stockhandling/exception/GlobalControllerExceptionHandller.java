package com.commercetools.stockhandling.exception;

import org.hibernate.StaleObjectStateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalControllerExceptionHandller {

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(NoSuchElementException.class)
    public void handleInvalidProductId() {}

    @ResponseStatus(HttpStatus.NO_CONTENT)  // 400
    @ExceptionHandler(StaleObjectStateException.class)
    public void handleStaleObjectStateException() {}
}
