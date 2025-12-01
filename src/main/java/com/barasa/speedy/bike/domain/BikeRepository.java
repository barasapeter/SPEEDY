package com.barasa.speedy.bike.domain;

import java.util.List;
import java.util.Optional;

public interface BikeRepository {
    Bike save(Bike bike);

    Optional<Bike> findByCode(String code);

    List<Bike> findAll();
}
