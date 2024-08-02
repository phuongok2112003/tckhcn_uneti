package com.example.tapchikhcn.exceptions;

import java.io.Serializable;

public class ApiMessageError implements ApiSubError, Serializable {
    private final String errorMessage;
    private final String value;

    public ApiMessageError(String errorMessage, String value) {
        this.errorMessage = errorMessage;
        this.value = value;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public String getValue() {
        return value;
    }
}
