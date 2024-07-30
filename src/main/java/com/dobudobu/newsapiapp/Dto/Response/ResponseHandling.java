package com.dobudobu.newsapiapp.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseHandling <T>{

    private T data;

    private String message;

    private HttpStatus httpStatus;

    private ZonedDateTime timeStamp = ZonedDateTime.now();

    private Boolean errors;

}
