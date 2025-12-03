package com.barasa.speedy;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.barasa.speedy.bike.domain.Bike;
import com.barasa.speedy.bike.domain.BikeService;
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

    public DashBoardController(UserService userService, ShopService shopService, BikeService bikeService) {
        this.userService = userService;
        this.shopService = shopService;
        this.bikeService = bikeService;
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

}
