package com.barasa.speedy;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.barasa.speedy.bike.domain.Bike;
import com.barasa.speedy.bike.domain.BikeService;
import com.barasa.speedy.session.domain.Session;
import com.barasa.speedy.session.domain.SessionService;
import com.barasa.speedy.shop.domain.Shop;
import com.barasa.speedy.shop.domain.ShopService;
import com.barasa.speedy.user.domain.User;
import com.barasa.speedy.user.domain.UserService;

import java.util.*;

@Controller
public class DashBoardController {

    private final UserService userService;
    private final ShopService shopService;
    private final BikeService bikeService;
    private final SessionService sessionService;

    public DashBoardController(
            UserService userService,
            ShopService shopService,
            BikeService bikeService,
            SessionService sessionService) {
        this.userService = userService;
        this.shopService = shopService;
        this.bikeService = bikeService;
        this.sessionService = sessionService;
    }

    @GetMapping("/dashboard")
    public String index(HttpServletRequest request, Model model, HttpSession session) {
        String userUuidStr = (String) session.getAttribute("USER_ID");

        if (userUuidStr == null) {
            return "redirect:/";
        }

        UUID userUuid;
        try {
            userUuid = UUID.fromString(userUuidStr);
        } catch (IllegalArgumentException e) {
            return "redirect:/";
        }

        Optional<User> userOpt = userService.findById(userUuid);

        if (userOpt.isEmpty()) {
            return "redirect:/";
        }

        User user = userOpt.get();

        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("shop", shopService.findByOwner(userUuid.toString()).orElse(null));

        return "dashboard";
    }

    @GetMapping("/bikes")
    public String bikes(HttpServletRequest request, Model model, HttpSession session) {
        String userUuidStr = (String) session.getAttribute("USER_ID");

        if (userUuidStr == null) {
            return "redirect:/";
        }

        UUID userUuid;
        try {
            userUuid = UUID.fromString(userUuidStr);
        } catch (IllegalArgumentException e) {
            return "redirect:/";
        }

        Optional<User> userOpt = userService.findById(userUuid);

        if (userOpt.isEmpty()) {
            return "redirect:/";
        }

        Optional<Shop> shopOpt = shopService.findByOwner(userOpt.get().getID());
        if (shopOpt.isEmpty()) {
            model.addAttribute("shop", null);
        } else {
            List<Bike> bikes = bikeService.findByShopUuid(shopOpt.get().getUuid());
            model.addAttribute("shop", shopService.findByOwner(userUuid.toString()).orElse(null));
            model.addAttribute("bikes", bikes);
        }

        return "bikes";
    }

    @GetMapping("/sessions")
    public String sessions(HttpServletRequest request, Model model, HttpSession session) {
        String userUuidStr = (String) session.getAttribute("USER_ID");

        if (userUuidStr == null) {
            return "redirect:/";
        }

        UUID userUuid;
        try {
            userUuid = UUID.fromString(userUuidStr);
        } catch (IllegalArgumentException e) {
            return "redirect:/";
        }

        Optional<User> userOpt = userService.findById(userUuid);

        if (userOpt.isEmpty()) {
            return "redirect:/";
        }

        Optional<Shop> shopOpt = shopService.findByOwner(userOpt.get().getID());
        if (shopOpt.isEmpty()) {
            model.addAttribute("shop", null);
        } else {
            List<Session> sessions = sessionService.findByShopUuid(shopOpt.get().getUuid());
            model.addAttribute("shop", shopService.findByOwner(userUuid.toString()).orElse(null));
            model.addAttribute("sessions", sessions);
        }

        return "sessions";
    }

    @GetMapping("/billing")
    public String billing(
            @RequestParam("uuid") UUID billingUuid,
            HttpServletRequest request,
            Model model,
            HttpSession session) {
        String userUuidStr = (String) session.getAttribute("USER_ID");

        if (userUuidStr == null) {
            return "redirect:/";
        }

        UUID userUuid;
        try {
            userUuid = UUID.fromString(userUuidStr);
        } catch (IllegalArgumentException e) {
            return "redirect:/";
        }

        Optional<User> userOpt = userService.findById(userUuid);

        if (userOpt.isEmpty()) {
            return "redirect:/";
        }

        Optional<Session> sessionOpt = sessionService.findById(billingUuid);
        System.out.println(sessionOpt.get());

        return "billing";
    }

    @GetMapping("/billings")
    public String getBillings(
            HttpServletRequest request,
            Model model,
            HttpSession session) {
        String userUuidStr = (String) session.getAttribute("USER_ID");

        if (userUuidStr == null) {
            return "redirect:/";
        }

        UUID userUuid;
        try {
            userUuid = UUID.fromString(userUuidStr);
        } catch (IllegalArgumentException e) {
            return "redirect:/";
        }

        Optional<User> userOpt = userService.findById(userUuid);

        if (userOpt.isEmpty()) {
            return "redirect:/";
        }

        List<Session> sessionOpt = sessionService.findByUserUuid(userOpt.get().getUuid());
        if (sessionOpt.isEmpty()) {
            model.addAttribute("sessions", null);
        } else {
            List<Session> sessions = sessionService.findByUserUuid(userOpt.get().getUuid());
            model.addAttribute("shop", shopService.findByOwner(userUuid.toString()).orElse(null));
            model.addAttribute("sessions", sessions);
        }

        return "billings";
    }

}
