package com.barasa.speedy.session.infrastructure;

import com.barasa.speedy.bike.infrastructure.BikeEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface BikeJpaRepository extends JpaRepository<BikeEntity, String> {
    List<BikeEntity> findByShopUuid(UUID shopUuid);
}
