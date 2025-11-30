package com.barasa.speedy.config;

// Request Filtering
// Per-Request Validation. This custom OncePerRequestFilter intercepts every request:

// ----------------------------------------------------------------------------------------------------------------------------
// 1. Extracts the JWT from the `Authorization` header.
// 2. Uses the `JwtTokenService` to validate the signature and extract the user's `uuid`.
// 3. If valid, loads the user details (e.g., from a Spring `UserDetailsService`) and sets the `Authentication` object in the Security Context, allowing the request to proceed to the controller. |
// ----------------------------------------------------------------------------------------------------------------------------

public class JwtRequestFilter {

}
