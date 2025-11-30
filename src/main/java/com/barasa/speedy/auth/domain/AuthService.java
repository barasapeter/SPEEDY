package com.barasa.speedy.auth.domain;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.barasa.speedy.auth.web.dto.AuthResponse;
import com.barasa.speedy.user.domain.UserService;
import com.barasa.speedy.user.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public AuthResponse authenticateAndGenerateToken(String identifier, String rawPassword) {

        // 1. Find the user by identifier (e.g., email)
        // NOTE: You would need to implement a method in UserService/UserRepository to
        // find by identifier.
        // We'll simulate finding the user here:
        User user = new User(null);

        // 2. Verify the password
        // The actual stored hashed password would be on the User object (e.g.,
        // user.getHashedPassword())
        // if (!passwordEncoder.matches(rawPassword, user.getHashedPassword())) {
        // throw new BadCredentialsException("Invalid username or password");
        // }

        // 3. Generate the JWT token
        String token = "jwtTokenService.generateToken(user.getUuid())";

        // 4. Return the response DTO
        return new AuthResponse(token, user.getUuid());
    }

    // Placeholder method required for the logic above
    public User findUserByIdentifier(String identifier) {
        // In a real app, this would delegate to UserRepository
        // For demonstration, simulating success:
        return new User(java.util.UUID.fromString("7fdb48df-aa38-40b9-8068-07e36611f7c0"), "Jane Doe", "555-1234",
                null);
    }
}
