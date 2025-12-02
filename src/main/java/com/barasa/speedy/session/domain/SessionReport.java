package com.barasa.speedy.session.domain;

import com.barasa.speedy.session.infrastructure.SessionReportEntity;
import lombok.*;
import java.util.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionReport {

    private UUID sessionUuid;
    private Integer dim;
    private String crid;
    private String txndesc;
    private BigDecimal amountCharged;
    private String txnCode;
    private Map<String, Object> addinfo;

    public static SessionReport fromEntity(SessionReportEntity e) {
        if (e == null)
            return null;
        return SessionReport.builder()
                .sessionUuid(e.getSession() != null ? e.getSession().getUuid() : e.getSessionUuid())
                .dim(e.getDim())
                .crid(e.getCrid())
                .txndesc(e.getTxndesc())
                .amountCharged(e.getAmountCharged())
                .txnCode(e.getTxnCode())
                .addinfo(e.getAddinfo())
                .build();
    }
}
