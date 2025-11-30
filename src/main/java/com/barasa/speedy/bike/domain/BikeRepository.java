package com.barasa.speedy.bike.domain;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface BikeRepository {

    Optional<Bike> findByCode(String code);

    List<Bike> findByShopUuid(UUID shopUuid);

    Bike save(Bike bike);

    void delete(Bike bike);
}
