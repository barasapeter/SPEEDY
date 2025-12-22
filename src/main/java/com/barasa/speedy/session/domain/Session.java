package com.barasa.speedy.session.domain;

import com.barasa.speedy.session.infrastructure.SessionEntity;

import lombok.*;
import java.util.*;

import java.time.Instant;

import java.text.NumberFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {

    private UUID uuid;
    private String userName;
    private Double rpm;
    private UUID shopUuid;
    private String bikeCode;
    private UUID userUuid;
    private Instant startTime;
    private Instant stopTime;

    public static Session fromEntity(SessionEntity e) {
        if (e == null)
            return null;

        return Session.builder()
                .uuid(e.getUuid())
                .shopUuid(e.getShop() != null ? e.getShop().getUuid() : null)
                .bikeCode(e.getBike() != null ? e.getBike().getCode() : null)
                .userUuid(e.getUser() != null ? e.getUser().getUuid() : null)
                .startTime(e.getStartTime())
                .stopTime(e.getStopTime())
                .userName(e.getUser() != null ? e.getUser().getName() : null)
                .rpm(e.getBike() != null ? e.getBike().getRpm() : null)
                .build();
    }

    public SessionReport toReport(
            Integer dim,
            String crid,
            String txndesc,
            java.math.BigDecimal amountCharged,
            String txnCode,
            Map<String, Object> addinfo) {

        return SessionReport.builder()
                .sessionUuid(this.uuid)
                .dim(dim)
                .crid(crid)
                .txndesc(txndesc)
                .amountCharged(amountCharged)
                .txnCode(txnCode)
                .addinfo(addinfo)
                .build();
    }

    public String getBikeCode() {
        return this.bikeCode;
    }

    public String getUserFullNames() {
        return this.userName;
    }

    public String getChargeNowFormatted() {
        long charge = getChargeNow();
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("en-KE"));
        return "Kshs. " + nf.format(charge);
    }

    public Double getDurationInMinutes() {
        if (startTime == null || rpm == null) {
            return 0D;
        }

        Instant end = (stopTime != null) ? stopTime : Instant.now();
        long seconds = java.time.Duration.between(startTime, end).getSeconds();
        if (seconds < 0)
            seconds = 0;

        double minutesFraction = seconds / 60.0;
        return minutesFraction;

    }

    public long getChargeNow() {
        if (startTime == null || rpm == null) {
            return 0L;
        }

        Instant end = (stopTime != null) ? stopTime : Instant.now();
        long seconds = java.time.Duration.between(startTime, end).getSeconds();
        if (seconds < 0)
            seconds = 0;

        double minutesFraction = seconds / 60.0;
        double rawCharge = minutesFraction * rpm;
        long rounded = Math.round(rawCharge / 10.0) * 10;

        return rounded;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public Instant getStopTime() {
        return this.stopTime;
    }

    public String getPictureURL() {
        return "/me.jpg";
    }

    public UUID getUuid() {
        return this.uuid;
    }
}
