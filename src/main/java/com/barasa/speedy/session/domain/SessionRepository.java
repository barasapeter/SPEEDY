package com.barasa.speedy.session.domain;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface SessionRepository {

    Optional<Session> findByUuid(UUID uuid);

    List<Session> findByBikeCode(String bikeCode);

    List<Session> findByUserUuid(UUID userUuid);

    Session save(Session session);
}
