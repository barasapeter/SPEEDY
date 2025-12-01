package com.barasa.speedy.bike.domain;

import com.barasa.speedy.bike.infrastructure.BikeEntity;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bike {

    private String code;
    private Double rpm;
    private UUID shopUuid;
    private String addinfo;

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
}
