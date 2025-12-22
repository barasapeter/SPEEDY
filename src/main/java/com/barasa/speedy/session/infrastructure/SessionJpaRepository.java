package com.barasa.speedy.session.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SessionJpaRepository extends JpaRepository<SessionEntity, UUID> {
    // List<SessionEntity> findByShopUuid(UUID id);

    List<SessionEntity> findByShopUuidAndStopTimeIsNull(UUID shopUuid);
}
