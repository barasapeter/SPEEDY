package com.barasa.speedy.shop.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ShopJpaRepository extends JpaRepository<ShopEntity, UUID> {
    List<ShopEntity> findByOwnerUuid(UUID ownerUuid);
}
