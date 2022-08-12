package com.springboot.school.exception;

import org.springframework.http.HttpStatus;

public class SchoolApiException extends RuntimeException{

    private HttpStatus httpStatus;
    private String message;

    public SchoolApiException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public SchoolApiException(String message, HttpStatus httpStatus, String message1) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message1;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
