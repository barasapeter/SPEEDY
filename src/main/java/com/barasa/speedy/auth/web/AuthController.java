package com.barasa.speedy.auth.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barasa.speedy.common.util.PhoneNumberValidatorAndStandardizer;
import com.barasa.speedy.user.domain.User;
import com.barasa.speedy.user.domain.UserService;
import com.barasa.speedy.auth.domain.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> authRegister(@RequestBody Map<String, Object> payload) {
        Map<String, String> registerReturnMessage = new HashMap<>();

        String name = (String) payload.get("name");
        String phone = (String) payload.get("phone");
        String email = (String) payload.get("email");
        String password = (String) payload.get("password");

        phone = PhoneNumberValidatorAndStandardizer.standardizePhone(phone);
        if (phone == null) {
            registerReturnMessage.put("title", "Invalid Number");
            registerReturnMessage.put("message",
                    "Failed to create account. Phone number " + phone + " is invalid. Please try again.");
            return ResponseEntity.badRequest().body(registerReturnMessage);
        }

        if (userService.findByPhone(phone).isPresent()) {
            registerReturnMessage.put("title", "Number Registered");
            registerReturnMessage.put("message", "Phone number already registered.");
            return ResponseEntity.badRequest().body(registerReturnMessage);
        }

        if (userService.findByEmail(email).isPresent()) {
            registerReturnMessage.put("title", "Email Registered");
            registerReturnMessage.put("message", "Email is already registered.");
            return ResponseEntity.badRequest().body(registerReturnMessage);
        }

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

        registerReturnMessage.put("title", "Bravo!");
        registerReturnMessage.put("message",
                "User account created successfully! Please Log in and Enjoy the full Speedy Experience.");
        return ResponseEntity.ok(registerReturnMessage);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");

        Optional<User> userOpt = userService.findByEmail(email);
        Map<String, String> response = new HashMap<>();

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String storedPassword = (String) user.getAddinfo().get("password");
            if (storedPassword.equals(password)) {
                JwtUtil jwtUtil = new JwtUtil();
                String token = jwtUtil.generateToken(email);
                response.put("message", "Login: accepted");
                response.put("token", token);
                response.put("cookie", user.getUuid().toString());
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Login Failed. The sign-in details are incorrect.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } else {
            response.put("message", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
