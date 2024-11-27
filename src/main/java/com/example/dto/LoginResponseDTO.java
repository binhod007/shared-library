package com.example.dto;

public class LoginResponseDTO {
    private String token;
    private String email;

    public LoginResponseDTO(String token, String email) {
        this.token = token;
        this.email = email;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
