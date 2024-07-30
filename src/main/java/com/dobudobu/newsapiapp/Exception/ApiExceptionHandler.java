package com.dobudobu.newsapiapp.Exception;

import com.dobudobu.newsapiapp.Exception.ExceptionResponse.ApiException;
import com.dobudobu.newsapiapp.Exception.ServiceExceptionHandler.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Asia/Jakarta")),
                true
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {CustomNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(CustomNotFoundException customNotFoundException){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(customNotFoundException.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("Asia/Jakarta")),
                true
        );
        return new ResponseEntity<>(apiException, notFound);
    }

    @ExceptionHandler(value = {CustomFailUploadImageException.class})
    public ResponseEntity<Object> customFailUploadImageException(CustomFailUploadImageException customFailUploadImageException){
        HttpStatus failUpload = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                customFailUploadImageException.getMessage(),
                failUpload,
                ZonedDateTime.now(ZoneId.of("Asia/Jakarta")),
                true
        );
        return new ResponseEntity<>(apiException, failUpload);
    }

    @ExceptionHandler(value = {CustomDataAlreadyExistsException.class})
    public ResponseEntity<Object> customDataAlreadyExistsException(CustomDataAlreadyExistsException customDataAlreadyExistsException){
        HttpStatus alreadyExists = HttpStatus.CONFLICT;
        ApiException apiException = new ApiException(
                customDataAlreadyExistsException.getMessage(),
                alreadyExists,
                ZonedDateTime.now(ZoneId.of("Asia/Jakarta")),
                true
        );
        return new ResponseEntity<>(apiException, alreadyExists);
    }

    @ExceptionHandler(value = {CustomInternalServerErrorException.class})
    public ResponseEntity<Object> customInternalServerErrorException(CustomInternalServerErrorException customInternalServerErrorException){
        HttpStatus serverError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException(
                customInternalServerErrorException.getMessage(),
                serverError,
                ZonedDateTime.now(ZoneId.of("Asia/Jakarta")),
                true
        );
        return new ResponseEntity<>(apiException, serverError);
    }
}
