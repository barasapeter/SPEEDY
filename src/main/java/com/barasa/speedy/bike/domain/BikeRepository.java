package com.barasa.speedy.bike.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BikeRepository {
    Bike save(Bike bike);

    List<Bike> findAll();

    Optional<Bike> findByCode(String code);

    List<Bike> findByShopUuid(UUID uuid);

    void delete(Bike bike);

}
