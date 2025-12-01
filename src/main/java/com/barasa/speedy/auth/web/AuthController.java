package com.barasa.speedy.auth.web;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> authRegister() {
        Map<String, String> returnMessage = new HashMap<>();
        returnMessage.put("message", "Hello From Hello With Status Code!");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(returnMessage);
    }

}
