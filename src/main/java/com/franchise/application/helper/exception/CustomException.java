package com.franchise.application.helper.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public abstract class CustomException extends RuntimeException{

    private final String errorCode;
    private final Map<String, String> details;

    protected CustomException(String message, String errorCode, Map<String, String> details) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
    }
}
