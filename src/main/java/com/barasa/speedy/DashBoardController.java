package com.barasa.speedy;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashBoardController {

    @GetMapping("/dashboard")
    public String index(HttpServletRequest request, Model model, HttpSession session) {
        String userUuid = (String) session.getAttribute("USER_ID");

        System.out.println("User UUID: " + userUuid);

        if (userUuid == null) {
            return "redirect:/";
        }

        return "dashboard";
    }
}
