package com.example.manage.core.exception;

import com.example.manage.core.ResultCodes;

public class CustomException extends RuntimeException{
    private final int ERR_CODE;

    public CustomException(ResultCodes code) {
        super(code.getReasonPhrase());
        this.ERR_CODE = code.value();
    }

    public CustomException(ResultCodes code, String message) {
        super(message);
        this.ERR_CODE = code.value();
    }

    public int getErrorCode() {
        return this.ERR_CODE;
    }
}
