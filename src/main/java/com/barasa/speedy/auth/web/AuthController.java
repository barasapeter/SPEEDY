package com.barasa.speedy.auth.web;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.barasa.speedy.user.domain.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.barasa.speedy.user.domain.User;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> authRegister(@RequestBody Map<String, Object> payload) {
        Map<String, String> returnMessage = new HashMap<>();

        String name = (String) payload.get("name");
        String phone = (String) payload.get("phone");
        String email = (String) payload.get("email");
        String password = (String) payload.get("password");

        Map<String, String> addInfoMap = new HashMap<>();
        addInfoMap.put("email", email);
        addInfoMap.put("password", password);

        ObjectMapper mapper = new ObjectMapper();
        String addinfoJson = "";

        try {
            addinfoJson = mapper.writeValueAsString(addInfoMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            returnMessage.put("message", "Failed to process user info");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(returnMessage);
        }

        User user = User.builder()
                .uuid(UUID.randomUUID())
                .name(name)
                .phone(phone)
                .addinfo(addinfoJson)
                .build();

        userService.save(user);
        returnMessage.put("message", "User created successfully");
        return ResponseEntity.status(HttpStatus.OK).body(returnMessage);
    }

}
