package com.barasa.speedy.auth.web;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.barasa.speedy.user.domain.UserService;
import com.barasa.speedy.user.domain.User;
import com.barasa.speedy.common.util.*;

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

        phone = PhoneNumberValidatorAndStandardizer.standardizePhone(phone);
        if (phone == null) {
            returnMessage.put("title", "Invalid Number");
            returnMessage.put("message",
                    "Failed to create account. Phone number " + phone + " is invalid. Please try again.");
            return ResponseEntity.badRequest().body(returnMessage);
        }

        if (userService.findByPhone(phone).isPresent()) {
            returnMessage.put("title", "Number Registered");
            returnMessage.put("message", "Phone number already registered.");
            return ResponseEntity.badRequest().body(returnMessage);
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

        returnMessage.put("title", "Bravo!");
        returnMessage.put("message",
                "User account created successfully! Please Log in and Enjoy the full Speedy Experience.");
        return ResponseEntity.ok(returnMessage);
    }

}
