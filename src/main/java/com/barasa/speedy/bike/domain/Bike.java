package com.barasa.speedy.bike.domain;

import com.barasa.speedy.bike.infrastructure.BikeEntity;
import lombok.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bike {

    private String code;
    private Double rpm;
    private UUID shopUuid;
    private Map<String, Object> addinfo;

    public static Bike fromEntity(BikeEntity entity) {
        return Bike.builder()
                .code(entity.getCode())
                .rpm(entity.getRpm())
                .shopUuid(entity.getShop() != null ? entity.getShop().getUuid() : null)
                .addinfo(entity.getAddinfo())
                .build();
    }

    public void updateRentalRate(Double newRpm) {
        if (newRpm == null || newRpm <= 0) {
            throw new IllegalArgumentException("RPM must be positive.");
        }
        this.rpm = newRpm;
    }

    public String getCode() {
        return this.code;
    }

    public Double getRpm() {
        return this.rpm;
    }

    public String getPictureURL() {
        // return (String) addinfo.get("pictureURL");
        return "/me.jpg";
    }

    public String getAvailabilityStatus() {
        return "Unavailable";
    }
}
