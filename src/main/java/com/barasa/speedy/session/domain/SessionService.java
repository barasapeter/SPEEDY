package com.barasa.speedy.session.domain;

import lombok.RequiredArgsConstructor;
import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    // dependencies on other domains are injected here
    // private final BikeService bikeService;

    public Session startSession(UUID userUuid, String bikeCode) {
        // business Rule --- check if the user exists and if the bike is currently
        // available.

        UUID newUuid = UUID.randomUUID();
        Instant now = Instant.now();

        Session newSession = new Session(newUuid, userUuid, bikeCode, now);
        return sessionRepository.save(newSession);
    }
}
