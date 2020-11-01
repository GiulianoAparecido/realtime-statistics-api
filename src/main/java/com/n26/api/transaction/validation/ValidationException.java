package com.n26.api.transaction.validation;

import org.springframework.http.HttpStatus;

public class ValidationException extends RuntimeException{
    private final HttpStatus httpStatus;

    public ValidationException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus(){
        return httpStatus;
    }
}
