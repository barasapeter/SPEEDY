package com.barasa.speedy.session.domain;

import com.barasa.speedy.bike.infrastructure.BikeEntity;
import com.barasa.speedy.session.infrastructure.*;
import com.barasa.speedy.user.infrastructure.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SessionService implements SessionRepository {

    private final SessionJpaRepository sessionJpa;
    private final SessionReportJpaRepository reportJpa;

    @Override
    public Session save(Session session) {
        SessionEntity entity = SessionEntity.builder()
                .uuid(session.getUuid())
                .startTime(session.getStartTime())
                .stopTime(session.getStopTime())
                .bike(BikeEntity.builder().code(session.getBikeCode()).build())
                .user(UserEntity.builder().uuid(session.getUserUuid()).build())
                .build();

        sessionJpa.save(entity);
        return session;
    }

    @Override
    public Optional<Session> findById(UUID id) {
        return sessionJpa.findById(id).map(Session::fromEntity);
    }

    @Override
    public List<Session> findAll() {
        return sessionJpa.findAll().stream().map(Session::fromEntity).toList();
    }

    @Override
    public SessionReport saveReport(SessionReport report) {
        SessionReportEntity entity = SessionReportEntity.builder()
                .sessionUuid(report.getSessionUuid())
                .dim(report.getDim())
                .crid(report.getCrid())
                .txndesc(report.getTxndesc())
                .amountCharged(report.getAmountCharged())
                .txnCode(report.getTxnCode())
                .logs(report.getLogs())
                .session(SessionEntity.builder().uuid(report.getSessionUuid()).build())
                .build();

        reportJpa.save(entity);
        return report;
    }
}