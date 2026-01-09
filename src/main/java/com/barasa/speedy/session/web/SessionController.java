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

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
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
    public ResponseEntity<Map<String, String>> createSession(@RequestBody Map<String, Object> payload,
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
        Optional<Session> existingSession = sessionService.findById(sessionUuid);
        if (existingSession.isPresent()) {
            result.put("message", "A session already exists. Start time: " + existingSession.get().getStartTime());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        String bikeCode = (String) payload.get("bikeCode");
        String collateral = (String) payload.get("collateral");
        String customerName = (String) payload.get("customerName");
        String registration = (String) payload.get("registration");
        String phone = (String) payload.get("phone");

        Map<String, Object> addinfo = new HashMap<>();
        addinfo.put("registration", registration);
        addinfo.put("toReportCollateral", collateral);
        addinfo.put("userType", "customer");

        final String customerPhone = PhoneNumberValidatorAndStandardizer.standardizePhone(phone);
        if (customerPhone == null) {
            result.put("title", "Invalid Number");
            result.put("message",
                    "Failed to create account. Phone number " + customerPhone + " is invalid. Please try again.");
            return ResponseEntity.badRequest().body(result);
        }

        Optional<Bike> bikeOpt = bikeService.findByCode(bikeCode);

        if (!bikeOpt.isPresent()) {
            result.put("message", "Bike " + bikeCode + " not Found. Please check the code and try again.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        // todo: check bike availability - in terms of session

        // check if user exists
        Optional<User> customerOpt = userService.findByPhone(customerPhone);
        UUID userCustomerUuid = customerOpt
                .map(User::getUuid) // if exists → their UUID
                .orElseGet(() -> {
                    UUID id = UUID.randomUUID();
                    User customer = User.builder()
                            .uuid(id)
                            .name(customerName)
                            .phone(customerPhone)
                            .addinfo(addinfo)
                            .build();
                    userService.save(customer);
                    return id;
                });

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

    @PostMapping("/stop")
    public ResponseEntity<Map<String, String>> stopOngoingSession(@RequestBody Map<String, Object> payload,
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
        Optional<Session> existingSession = sessionService.findById(sessionUuid);
        if (!existingSession.isPresent()) {
            result.put("message", "Invalid Session ID passed.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            Session updatedSession = existingSession.get();
            updatedSession.setStopTime(Instant.now());
            sessionService.save(updatedSession);
            result.put("message",
                    "Session Stopped successfully. Total cost: " + updatedSession.getChargeNowFormatted());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }

    }

    @PostMapping("/billing")
    public ResponseEntity<Map<String, String>> billing(@RequestBody Map<String, Object> payload,
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
        Optional<Session> existingSession = sessionService.findById(sessionUuid);
        if (!existingSession.isPresent()) {
            result.put("message", "Invalid Session ID passed.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            Session updatedSession = existingSession.get();
            updatedSession.setStopTime(Instant.now());
            sessionService.save(updatedSession);

            Map<String, Object> addinfoStarter = new HashMap<>();
            Map<String, Object> logEntity = new HashMap<>();
            logEntity.put("activityArray", new ArrayList<>().add("Session Stopped"));
            addinfoStarter.put("logEntity", logEntity);

            // create checkout request ID (crid)
            result.put("message",
                    "Billing Initiated successfully. Total cost: " + updatedSession.getChargeNowFormatted());
            SessionReport sessionReport = updatedSession.toReport(
                    ((int) Math.round(updatedSession.getDurationInMinutes())),
                    null,
                    null,
                    BigDecimal.valueOf(updatedSession.getChargeNow()),
                    null,
                    addinfoStarter);
            sessionService.saveReport(sessionReport);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }

    }

    @PostMapping("/initiate-stk-push")
    public ResponseEntity<Map<String, String>> initiateNiPush(@RequestBody Map<String, Object> payload,
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

        UUID sessionUuid = UUID.fromString((String) payload.get("sessionUuid"));
        String phoneNumber = (String) payload.get("phoneNumber");
        Optional<Session> existingSession = sessionService.findById(sessionUuid);
        if (!existingSession.isPresent()) {
            result.put("message", "Invalid Session ID passed.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            
            Session clientRequestSession = existingSession.get();
            result.put("message", "Initiating STK Push for session via " + phoneNumber);

            return ResponseEntity.status(HttpStatus.OK).body(result);
        }

    }

}
