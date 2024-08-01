package com.dobudobu.newsapiapp.Exception.ServiceCustomException;

public class CustomFailUploadImageException extends RuntimeException{

    public CustomFailUploadImageException(String message) {
        super(message);
    }

    public CustomFailUploadImageException(String message, Throwable cause) {
        super(message, cause);
    }
}
