package com.example.dto;

public class UserResponseDTO {
    private Long id;
    private String email;

    // Constructor with id and email
    public UserResponseDTO(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
