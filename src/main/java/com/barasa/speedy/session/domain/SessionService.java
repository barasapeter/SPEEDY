package com.barasa.speedy.session.domain;

import com.barasa.speedy.bike.infrastructure.BikeEntity;
import com.barasa.speedy.session.infrastructure.*;
import com.barasa.speedy.shop.infrastructure.ShopEntity;
import com.barasa.speedy.user.infrastructure.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService implements SessionRepository {

    private final SessionJpaRepository sessionJpa;
    private final SessionReportJpaRepository reportJpa;
    private final EntityManager em;

    @Override
    @Transactional
    public Session save(Session session) {
        BikeEntity bikeRef = null;
        UserEntity userRef = null;

        if (session.getBikeCode() != null) {
            bikeRef = em.getReference(BikeEntity.class, session.getBikeCode());
        }

        ShopEntity shopRef = null;
        if (session.getShopUuid() != null) {
            shopRef = em.getReference(ShopEntity.class, session.getShopUuid());
        }

        if (session.getUserUuid() != null) {
            userRef = em.getReference(UserEntity.class, session.getUserUuid());
        }

        if (session.getUserUuid() != null) {
            userRef = em.getReference(UserEntity.class, session.getUserUuid());
        }

        SessionEntity entity = SessionEntity.builder()
                .uuid(session.getUuid())
                .shop(shopRef)
                .startTime(session.getStartTime())
                .stopTime(session.getStopTime())
                .bike(bikeRef)
                .user(userRef)
                .build();

        SessionEntity saved = sessionJpa.save(entity);
        return Session.fromEntity(saved);
    }

    @Override
    public Optional<Session> findById(UUID id) {
        return sessionJpa.findById(id).map(Session::fromEntity);
    }

    // @Override
    // public List<Session> findByShopUuid(UUID id) {
    // return sessionJpa.findByShopUuid(id).stream()
    // .map(Session::fromEntity)
    // .toList();
    // }

    @Override
    public List<Session> findByShopUuid(UUID id) {
        return sessionJpa.findByShopUuidAndStopTimeIsNull(id).stream()
                .map(Session::fromEntity)
                .toList();
    }

    @Override
    public List<Session> findAll() {
        return sessionJpa.findAll().stream().map(Session::fromEntity).toList();
    }

    @Override
    @Transactional
    public SessionReport saveReport(SessionReport report) {
        SessionEntity sessionRef = em.getReference(SessionEntity.class, report.getSessionUuid());

        SessionReportEntity entity = SessionReportEntity.builder()
                .session(sessionRef)
                .dim(report.getDim())
                .crid(report.getCrid())
                .txndesc(report.getTxndesc())
                .amountCharged(report.getAmountCharged())
                .txnCode(report.getTxnCode())
                .addinfo(report.getAddinfo())
                .build();

        SessionReportEntity saved = reportJpa.save(entity);
        return SessionReport.fromEntity(saved);
    }
}
