package com.dobudobu.newsapiapp.Exception.ServiceExceptionHandler;

public class CustomFailUploadImageException extends RuntimeException{

    public CustomFailUploadImageException(String message) {
        super(message);
    }

    public CustomFailUploadImageException(String message, Throwable cause) {
        super(message, cause);
    }
}
