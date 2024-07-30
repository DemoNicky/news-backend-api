package com.dobudobu.newsapiapp.Exception.ServiceExceptionHandler;

public class CustomDataAlreadyExistsException extends RuntimeException{

    public CustomDataAlreadyExistsException(String message) {
        super(message);
    }

    public CustomDataAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
