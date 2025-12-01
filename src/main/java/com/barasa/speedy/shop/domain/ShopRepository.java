package com.barasa.speedy.shop.domain;

import java.util.*;

public interface ShopRepository {
    Shop save(Shop shop);

    Optional<Shop> findById(UUID id);

    List<Shop> findAll();
}
