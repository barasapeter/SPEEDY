package com.barasa.speedy.bike.infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeJpaRepository extends JpaRepository<BikeEntity, String> {
    List<BikeEntity> findByShopUuid(UUID uuid);
}
