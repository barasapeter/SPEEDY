package com.barasa.speedy.session.web;

import com.barasa.speedy.bike.domain.Bike;
import com.barasa.speedy.bike.domain.BikeService;
import com.barasa.speedy.session.domain.Session;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/session")
public class SessionController {

    private final UserService userService;
    private final ShopService shopService;
    private final BikeService bikeService;

    public SessionController(UserService userService, ShopService shopService, BikeService bikeService) {
        this.userService = userService;
        this.shopService = shopService;
        this.bikeService = bikeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> updateShopDetails(@RequestBody Map<String, Object> payload,
            HttpSession session) {
        Map<String, String> result = new HashMap<>();

        String userUuidStr = (String) session.getAttribute("USER_ID");

        UUID userUuid;
        try {
            userUuid = UUID.fromString(userUuidStr);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok(result);
        }

        Optional<User> userOpt = userService.findById(userUuid);

        if (userOpt.isEmpty()) {
            result.put("message", "You need to log in first.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }

        Optional<Shop> shopOpt = shopService.findByOwner(userOpt.get().getID());

        UUID sessionUuid = UUID.fromString((String) payload.get("sessionUuid"));
        String bikeCode = (String) payload.get("bikeCode");
        String collateral = (String) payload.get("collateral");
        String customerName = (String) payload.get("customerName");
        String registration = (String) payload.get("registration");

        Optional<Bike> bikeOpt = bikeService.findByCode(bikeCode);

        Bike bike;
        if (!bikeOpt.isPresent()) {
            result.put("message", "Bike " + bikeCode + " not Found. Please check the code and try again.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);

        // todo: check bike availability

        // check if user exists
        Optional<User> customerOpt = userService.findByPhone(bikeCode);

        // build user
        User user = User.builder()
                .uuid(UUID.randomUUID())
                .name(name)
                .phone(phone)
                .addinfo(addinfo)
                .build();

        userService.save(user);

        session = Session.builder()
                .uuid(sessionUuid)
                .shopUuid(shopOpt.get().getUuid())
                .bikeCode(bikeCode)
                .userUuid()

                .build();
        result.put("message", "Bike created successfully");

        
    }

}
