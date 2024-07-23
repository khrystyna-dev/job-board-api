package com.example.jobboardapi.controller;

import com.example.jobboardapi.exception.CustomHttpClientException;
import com.example.jobboardapi.exception.DataProcessingException;
import com.example.jobboardapi.exception.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * Global exception handler for handling specific exceptions across the application.
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles {@link DataProcessingException} and returns error details.
     *
     * @param exception The exception that occurred
     * @param request   The request during which the exception happened
     * @return {@link ResponseEntity} with {@link ErrorDetails} and a BAD_REQUEST status
     */
    @ExceptionHandler(DataProcessingException.class)
    public ResponseEntity<?> dataNotFoundExceptionHandling(Exception exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(new Date(), exception.getMessage(),
                request.getDescription(false)), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link CustomHttpClientException} and returns error details.
     *
     * @param exception The exception that occurred
     * @param request   The request during which the exception happened
     * @return {@link ResponseEntity} with {@link ErrorDetails} and a BAD_REQUEST status
     */
    @ExceptionHandler(CustomHttpClientException.class)
    public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(new Date(), exception.getMessage(),
                request.getDescription(false)), HttpStatus.BAD_REQUEST);
    }
}
