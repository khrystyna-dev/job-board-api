package com.example.jobboardapi.exception;

/**
 * Custom exception for handling HTTP client errors.
 */
public class CustomHttpClientException extends RuntimeException {

    /**
     * Constructs a new DataProcessingException with the specified detail message.
     *
     * @param message The error message.
     */
    public CustomHttpClientException(String message) {
        super(message);
    }
}
