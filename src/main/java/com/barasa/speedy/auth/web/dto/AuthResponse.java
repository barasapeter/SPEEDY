package com.barasa.speedy.auth.web.dto;

// Data Transfer
// Contains the generated JWT string and, optionally, the user's UUID.

import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class AuthResponse {

    private final String jwtToken;
    private final UUID userUuid;
    private final String tokenType = "Bearer";

}
