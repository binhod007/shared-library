package com.example.exception.core;

import com.example.exception.enums.Message;

public class ValidationException extends BaseException {
    public ValidationException(Message message) {
        super(message);
    }
}
