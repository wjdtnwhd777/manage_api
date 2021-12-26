package com.example.manage.core.exception;

import com.example.manage.core.ResultCodes;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super(ResultCodes.USER_NOT_FOUND_EXCEPTION);
    }

    public UserNotFoundException(String message) {
        super(ResultCodes.USER_NOT_FOUND_EXCEPTION, message);
    }
}