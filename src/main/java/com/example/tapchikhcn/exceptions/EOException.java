package com.example.tapchikhcn.exceptions;


import com.example.tapchikhcn.error.ErrorStatus;
import com.example.tapchikhcn.utils.I18n;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

public class EOException extends RuntimeException{
    protected final int code;
    protected final String message;
    protected final String value;


    public EOException(HttpStatus httpStatus, String messageUrl, @Nullable String value, Object... arg) {
        this.code = httpStatus.value();
        this.value = value;
        this.message = I18n.get(messageUrl, arg);
    }

    public EOException(int code, String messageUrl, @Nullable String value, Object... arg) {
        this.code = code;
        this.value = value;
        this.message = I18n.get(messageUrl, arg);
    }

    public EOException(ErrorStatus enumError, Object... arg) {
        this.code = enumError.getCode();
        this.value = null;
        this.message = I18n.get(enumError.getMessage(), arg);
    }
}
