package com.dobudobu.newsapiapp.Exception.ServiceCustomException;

public class CustomInternalServerErrorException extends RuntimeException{

    public CustomInternalServerErrorException(String message) {
        super(message);
    }

    public CustomInternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
