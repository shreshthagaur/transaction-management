package com.transfer.money.api.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiError {

    private HttpStatus status;
    private String message;


    public ApiError(final String message){
        this.message = message;
    }

    public ApiError(final HttpStatus httpStatus , final String message){
        this.status = httpStatus;
        this.message = message;
    }
}
