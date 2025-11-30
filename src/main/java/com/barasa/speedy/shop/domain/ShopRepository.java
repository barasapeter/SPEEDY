package com.barasa.speedy.shop.domain;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface ShopRepository {

    Optional<Shop> findByUuid(UUID uuid);

    List<Shop> findByOwnerUuid(UUID ownerUuid);

    Shop save(Shop shop);

    void delete(Shop shop);
}
