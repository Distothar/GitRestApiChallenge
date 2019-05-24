package com.Distothar.GitRestApiChallenge.Helper;

import com.Distothar.GitRestApiChallenge.Entity.ApiError;
import com.Distothar.GitRestApiChallenge.Entity.ValidationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestErrorHandler
        extends ResponseEntityExceptionHandler {
    /**
     * Handles InvalidOperations and creates a ValidationError with the input parameters
     * @param message
     * @param status
     * @return ResponseEntity containing a ValidationError
     */
    public static ResponseEntity<Object> handleInvalidOperation(String message, HttpStatus status) {
        ValidationError validationError = new ValidationError(status, message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(validationError, headers, status);

        return responseEntity;
    }

    /**
     * Handles exceptions and creates an ApiError containing the Exception
     * @param ex
     * @return ResponseEntity containing an ApiError
     */
    public static ResponseEntity<Object> handleUnexpectedError(Exception ex) {
        String message = "Unknown error";
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, message, ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(error, headers, HttpStatus.INTERNAL_SERVER_ERROR);

        return responseEntity;
    }
}