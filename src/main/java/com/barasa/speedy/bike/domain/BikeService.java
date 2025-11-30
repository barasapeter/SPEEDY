package com.barasa.speedy.bike.domain;

import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
public class BikeService {

    private final BikeRepository bikeRepository;

    public Bike getBikeByCode(String code) {
        return bikeRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Bike not found with code: " + code));
    }

    public Bike registerNewBike(String code, Double rpm, UUID shopUuid) {
        if (bikeRepository.findByCode(code).isPresent()) {
            throw new IllegalArgumentException("Bike code already exists.");
        }

        Bike newBike = new Bike(code, rpm, shopUuid, null);
        return bikeRepository.save(newBike);
    }
}
