package com.barasa.speedy.shop.web;

import com.barasa.speedy.shop.domain.ShopService;
import com.barasa.speedy.user.domain.User;
import com.barasa.speedy.user.domain.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/shop")
public class ShopController {

    private final UserService userService;

    public ShopController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String, String>> updateShopDetails(@RequestBody Map<String, Object> payload,
            HttpSession session) {
        Map<String, String> updateShopRequestResult = new HashMap<>();

        String userUuidStr = (String) session.getAttribute("USER_ID");

        UUID userUuid;
        try {
            userUuid = UUID.fromString(userUuidStr);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok(updateShopRequestResult);
        }

        Optional<User> userOpt = userService.findById(userUuid);

        if (userOpt.isEmpty()) {
            updateShopRequestResult.put("message", "You need to log in first.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(updateShopRequestResult);
        }

        updateShopRequestResult.put("message", "Shop details updated successfully.");
        return ResponseEntity.ok(updateShopRequestResult);
    }

}
