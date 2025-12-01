package com.barasa.speedy.shop.domain;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

public interface ShopRepository {
    Shop save(Shop shop);

    Optional<Shop> findById(UUID id);

    List<Shop> findAll();
}
