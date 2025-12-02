package com.barasa.speedy.auth.web;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.barasa.speedy.user.domain.UserService;
import com.barasa.speedy.user.domain.User;

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

        Map<String, Object> addinfo = new HashMap<>();
        addinfo.put("email", email);
        addinfo.put("password", password);

        User user = User.builder()
                .uuid(UUID.randomUUID())
                .name(name)
                .phone(phone)
                .addinfo(addinfo)
                .build();

        userService.save(user);

        returnMessage.put("message", "User created successfully");
        return ResponseEntity.ok(returnMessage);
    }

}
