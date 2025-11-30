package com.barasa.speedy.bike.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class Bike {

    private final String code;

    private Double rpm;

    private UUID shopUuid;

    private String addinfo;

    public Bike(String code, Double rpm, UUID shopUuid, String addinfo) {
        this.code = code;
        this.rpm = rpm;
        this.shopUuid = shopUuid;
        this.addinfo = addinfo;
    }

    public void updateRentalRate(Double newRpm) {
        if (newRpm <= 0) {
            throw new IllegalArgumentException("Rental rate must be positive.");
        }
        this.rpm = newRpm;
    }
}
