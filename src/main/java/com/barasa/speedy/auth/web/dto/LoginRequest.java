package com.barasa.speedy.auth.web.dto;

// Data Transfer

// Contains the username/email and password sent by the client for login.

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NotBlank(message = "Username or email is required")
    private String identifier;

    @NotBlank(message = "Password is required")
    private String password;

    public String getIdentifier() {
        return this.identifier;
    }

    public String getPassword() {
        return this.password;
    }
}
