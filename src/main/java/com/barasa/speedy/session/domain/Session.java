package com.barasa.speedy.session.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class Session {

    private final UUID uuid;

    private final UUID userUuid;
    private final String bikeCode;

    private Instant startTime;
    private Instant stopTime;

    private SessionReport report;

    public Session(UUID uuid, UUID userUuid, String bikeCode, Instant startTime) {
        this.uuid = uuid;
        this.userUuid = userUuid;
        this.bikeCode = bikeCode;
        this.startTime = startTime;
        this.stopTime = null;
        this.report = null;
    }

    public SessionReport endSession(Instant endTime, Double bikeRpm) {
        if (this.stopTime != null) {
            throw new IllegalStateException("Session is already ended.");
        }

        this.stopTime = endTime;
        long duration = ChronoUnit.MINUTES.between(this.startTime, endTime);

        SessionReport newReport = new SessionReport(
                this.uuid,
                duration,
                UUID.randomUUID().toString(),
                "Bike rental for " + duration + " minutes.",
                null,
                "PENDING",
                null);

        BigDecimal calculatedFee = newReport.calculateFee(bikeRpm);
        newReport.setAmountCharged(calculatedFee);
        newReport.setTransactionCode("CHARGED");

        this.report = newReport;
        return newReport;
    }
}
