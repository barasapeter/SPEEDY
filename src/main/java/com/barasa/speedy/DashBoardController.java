package com.barasa.speedy;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashBoardController {

    @GetMapping("/dashboard")
    public String index(HttpServletRequest request, Model model) {
        String username = (String) request.getAttribute("cookie");
        if (username == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("cookie", username);
        return "dashboard";
    }
}
