package com.barasa.speedy.shop.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ShopJpaRepository extends JpaRepository<ShopEntity, UUID> {
    Optional<ShopEntity> findByOwner(String owner);
}
