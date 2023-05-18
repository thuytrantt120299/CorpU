package com.example.corpu.error;

import lombok.Data;

@Data
public class ValidationException extends RuntimeException {
    String errorCode;
    String errorMessage;

    public ValidationException(String errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}