package com.Distothar.GitRestApiChallenge.Entity;

import org.springframework.http.HttpStatus;

public class ApiError {
    private HttpStatus status;
    private String message;
    private Exception exception;

    public ApiError(HttpStatus status, String message, Exception exception) {
        this.status = status;
        this.message = message;
        this.exception = exception;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Exception getException() {
        return exception;
    }
}
