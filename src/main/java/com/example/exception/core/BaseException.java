package com.example.exception.core;

import com.example.exception.base.SystemCodeAware;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final String code;
    private final String message;

    public BaseException(SystemCodeAware systemCode) {
        super(systemCode.getDesc());
        this.code = systemCode.getCode();
        this.message = systemCode.getDesc();
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
