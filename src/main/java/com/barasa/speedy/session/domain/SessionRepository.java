package com.barasa.speedy.session.domain;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

public interface SessionRepository {
    Session save(Session session);

    Optional<Session> findById(UUID id);

    List<Session> findAll();

    SessionReport saveReport(SessionReport report);

    List<Session> findByShopUuid(UUID shopUuid);
}
