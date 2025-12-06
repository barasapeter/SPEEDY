package com.barasa.speedy.session.domain;

import com.barasa.speedy.session.infrastructure.SessionEntity;
import com.barasa.speedy.shop.domain.Shop;

import lombok.*;
import java.util.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {

    private UUID uuid;
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
        return "Fardosa Mpenda Baskeli";
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public Instant getStopTime() {
        return this.stopTime;
    }

    public String getPictureURL() {
        return "/me.png";
    }

    public UUID getUuid() {
        return this.uuid;
    }
}

