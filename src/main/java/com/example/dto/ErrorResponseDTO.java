package com.example.dto;

public class ErrorResponseDTO {
    private final String code;
    private final String message;

    public ErrorResponseDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
