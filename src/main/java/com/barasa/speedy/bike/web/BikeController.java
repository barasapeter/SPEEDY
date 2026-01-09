package com.barasa.speedy.bike.web;

import com.barasa.speedy.bike.domain.Bike;
import com.barasa.speedy.bike.domain.BikeService;
import com.barasa.speedy.shop.domain.Shop;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/bike")
public class BikeController {

    private final UserService userService;
    private final ShopService shopService;
    private final BikeService bikeService;

    public BikeController(UserService userService, ShopService shopService, BikeService bikeService) {
        this.userService = userService;
        this.shopService = shopService;
        this.bikeService = bikeService;
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String, String>> updateOrCreateBike(
            @RequestBody Map<String, Object> payload,
            HttpSession session) {

        Map<String, String> result = new HashMap<>();

        String userUuidStr = (String) session.getAttribute("USER_ID");
        if (userUuidStr == null) {
            result.put("message", "You need to log in first.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }

        UUID userUuid;
        try {
            userUuid = UUID.fromString(userUuidStr);
        } catch (IllegalArgumentException e) {
            result.put("message", "Invalid user session.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }

        Optional<User> userOpt = userService.findById(userUuid);
        if (userOpt.isEmpty()) {
            result.put("message", "You need to log in first.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }

        Optional<Shop> shopOpt = shopService.findByOwner(userOpt.get().getID());
        if (shopOpt.isEmpty()) {
            result.put("message", "Your shop could not be found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        String bikeCode = (String) payload.get("bikeCode"); 
        Double rpm = Double.valueOf((String) payload.get("rpm"));

        if (Math.round(rpm) == 0) {
            result.put("message", "Invalid Amount for rpm.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        Optional<Bike> bikeOpt = bikeService.findByCode(bikeCode); 

        Bike bike;
        if (bikeOpt.isPresent()) {
            bike = bikeOpt.get();
            bike.setRpm(rpm);
            bike.setShopUuid(shopOpt.get().getUuid());
            result.put("message", "Bike updated successfully");
        } else {
            bike = Bike.builder()
                    .code(bikeCode)
                    .rpm(rpm)
                    .shopUuid(shopOpt.get().getUuid())
                    .build();
            result.put("message", "Bike created successfully");
        }

        bikeService.save(bike);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteBike(
            @RequestBody Map<String, Object> payload,
            HttpSession session) {

        Map<String, String> result = new HashMap<>();

        String userUuidStr = (String) session.getAttribute("USER_ID");
        if (userUuidStr == null) {
            result.put("message", "You need to log in first.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }

        UUID userUuid;
        try {
            userUuid = UUID.fromString(userUuidStr);
        } catch (IllegalArgumentException e) {
            result.put("message", "Invalid user session.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }

        Optional<User> userOpt = userService.findById(userUuid);
        if (userOpt.isEmpty()) {
            result.put("message", "You need to log in first.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }

        Optional<Shop> shopOpt = shopService.findByOwner(userOpt.get().getID());
        if (shopOpt.isEmpty()) {
            result.put("message", "Your shop could not be found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        String bikeCode = (String) payload.get("bikeCode");

        Optional<Bike> bikeOpt = bikeService.findByCode(bikeCode);

        if (bikeOpt.isPresent()) {
            Bike bike = bikeOpt.get();
            bikeService.delete(bike);
            result.put("message", "Bike deleted successfully");
        } else {
            result.put("message", "Bike not found");
        }

        return ResponseEntity.ok(result);
    }

}
