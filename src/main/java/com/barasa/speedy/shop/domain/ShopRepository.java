package com.barasa.speedy.shop.domain;

import java.util.*;

public interface ShopRepository {
    Shop save(Shop shop);

    Optional<Shop> findById(UUID id);

    Optional<Shop> findByOwner(String owner);

    List<Shop> findAll();
}
