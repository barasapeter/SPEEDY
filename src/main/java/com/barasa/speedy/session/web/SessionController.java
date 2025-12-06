package com.barasa.speedy.session.web;

import com.barasa.speedy.bike.domain.Bike;
import com.barasa.speedy.bike.domain.BikeService;
import com.barasa.speedy.common.util.PhoneNumberValidatorAndStandardizer;
import com.barasa.speedy.session.domain.Session;
import com.barasa.speedy.session.domain.SessionReport;
import com.barasa.speedy.session.domain.SessionService;
import com.barasa.speedy.shop.domain.Shop;
import com.barasa.speedy.shop.domain.ShopService;
import com.barasa.speedy.user.domain.User;
import com.barasa.speedy.user.domain.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/session")
public class SessionController {

    private final UserService userService;
    private final ShopService shopService;
    private final BikeService bikeService;
    private final SessionService sessionService;

    public SessionController(UserService userService, ShopService shopService, BikeService bikeService,
            SessionService sessionService) {
        this.userService = userService;
        this.shopService = shopService;
        this.bikeService = bikeService;
        this.sessionService = sessionService;
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

        if (shopOpt.isEmpty()) {
            result.put("message", "Shop not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        UUID sessionUuid = UUID.fromString((String) payload.get("sessionUuid"));
        String bikeCode = (String) payload.get("bikeCode");
        String collateral = (String) payload.get("collateral");
        String customerName = (String) payload.get("customerName");
        String registration = (String) payload.get("registration");
        String phone = (String) payload.get("phone");

        Map<String, Object> addinfo = new HashMap<>();
        addinfo.put("registration", registration);
        addinfo.put("toReportCollateral", collateral);
        addinfo.put("userType", "customer");

        phone = PhoneNumberValidatorAndStandardizer.standardizePhone(phone);
        if (phone == null) {
            result.put("title", "Invalid Number");
            result.put("message",
                    "Failed to create account. Phone number " + phone + " is invalid. Please try again.");
            return ResponseEntity.badRequest().body(result);
        }

        Optional<Bike> bikeOpt = bikeService.findByCode(bikeCode);

        if (!bikeOpt.isPresent()) {
            result.put("message", "Bike " + bikeCode + " not Found. Please check the code and try again.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        // todo: check bike availability - in terms of session

        // check if user exists
        Optional<User> customerOpt = userService.findByPhone(phone);
        UUID userCustomerUuid = customerOpt.map(User::getUuid)
                .orElseGet(() -> UUID.randomUUID());

        if (customerOpt.isEmpty()) {
            User customer = User.builder()
                    .uuid(userCustomerUuid)
                    .name(customerName)
                    .phone(phone)
                    .addinfo(addinfo)
                    .build();
            userService.save(customer);
            result.put("status", "New customer added successfully");
        } else {
            result.put("status", "Customer status found");
        }

        System.out.println("Shop UUID:: " + shopOpt.get().getUuid());

        Instant startTime = Instant.now();
        Session bikeSession = Session.builder()
                .uuid(sessionUuid)
                .shopUuid(shopOpt.get().getUuid())
                .bikeCode(bikeCode)
                .userUuid(userCustomerUuid)
                .startTime(startTime)
                .build();

        sessionService.save(bikeSession);

        result.put("message", "Session created successfully. Start time: " + startTime);

        return ResponseEntity.ok(result);

    }

}
