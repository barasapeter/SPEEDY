package com.barasa.speedy.auth.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.barasa.speedy.auth.domain.AuthService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String index(Model model) {
        return "index";
    }

    @PostMapping
    public String login() {
        authService.authenticateAndGenerateToken(null, null);
        return "entity";
    }

}
