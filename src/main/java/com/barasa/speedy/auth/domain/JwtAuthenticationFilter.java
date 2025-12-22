// package com.barasa.speedy.auth.domain;

// import java.io.IOException;

// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// @Component
// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     private final JwtUtil jwtUtil;

//     public JwtAuthenticationFilter(JwtUtil jwtUtil) {
//         this.jwtUtil = jwtUtil;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request,
//             HttpServletResponse response,
//             FilterChain filterChain) throws ServletException, IOException {

//         String path = request.getRequestURI();

//         if (isPublic(path)) {
//             filterChain.doFilter(request, response);
//             return;
//         }

//         String authHeader = request.getHeader("Authorization");
//         if (authHeader != null && authHeader.startsWith("Bearer ")) {
//             String token = authHeader.substring(7);

//             if (jwtUtil.validateToken(token)) {
//                 request.setAttribute("cookie", jwtUtil.extractUserUuid(token));
//                 filterChain.doFilter(request, response);
//                 return;
//             } else {
//                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                 response.getWriter().write("{\"error\": \"Invalid JWT\"}");
//                 return;
//             }
//         }

//         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//         response.getWriter().write("{\"error\": \"Missing JWT\"}");
//     }

//     private boolean isPublic(String path) {
//         return path.equals("/")
//                 || path.equals("/login")
//                 || path.equals("/register")
//                 || path.matches(".*\\.(js|css|png|jpg|jpeg|svg|ico)$"); // static files
//     }
// }
