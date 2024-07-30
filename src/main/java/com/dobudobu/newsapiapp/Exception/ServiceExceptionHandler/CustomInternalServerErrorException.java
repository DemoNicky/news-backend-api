package com.dobudobu.newsapiapp.Exception.ServiceExceptionHandler;

public class CustomInternalServerErrorException extends RuntimeException{

    public CustomInternalServerErrorException(String message) {
        super(message);
    }

    public CustomInternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
