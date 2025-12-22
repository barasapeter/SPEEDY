// package com.barasa.speedy.auth.domain;

// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.security.Keys;
// import io.jsonwebtoken.Claims;
// import java.util.Date;
// import java.security.Key;

// import org.springframework.stereotype.Component;

// @Component
// public class JwtUtil {

//     private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

//     private final long expiration = 1000 * 60 * 60;

//     public String generateToken(String username) {
//         return Jwts.builder()
//                 .setSubject(username)
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                 .signWith(key)
//                 .compact();
//     }

//     public boolean validateToken(String token) {
//         try {
//             Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
//             return true;
//         } catch (Exception e) {
//             return false;
//         }
//     }

//     public String extractUserUuid(String token) {
//         Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
//         return claims.getSubject();
//     }
// }
