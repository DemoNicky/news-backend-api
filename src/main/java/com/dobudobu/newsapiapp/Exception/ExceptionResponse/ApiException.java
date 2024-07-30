package com.dobudobu.newsapiapp.Exception.ExceptionResponse;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {

    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timeStamp;
    private final Boolean errors;

    public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timeStamp, Boolean errors) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public Boolean getError() {
        return errors;
    }
}
