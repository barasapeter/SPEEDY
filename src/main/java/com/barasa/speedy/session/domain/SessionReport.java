package com.barasa.speedy.session.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class SessionReport {

    private final UUID sessionUuid;

    private Long durationInMinutes;
    private String checkoutRequestId;
    private String transactionDescription;
    private BigDecimal amountCharged;
    private String transactionCode;
    private String logs;

    public SessionReport(
            UUID sessionUuid,
            Long durationInMinutes,
            String checkoutRequestId,
            String transactionDescription,
            BigDecimal amountCharged,
            String transactionCode,
            String logs) {
        this.sessionUuid = sessionUuid;
        this.durationInMinutes = durationInMinutes;
        this.checkoutRequestId = checkoutRequestId;
        this.transactionDescription = transactionDescription;
        this.amountCharged = amountCharged;
        this.transactionCode = transactionCode;
        this.logs = logs;
    }

    public BigDecimal calculateFee(Double rpm) {
        if (durationInMinutes == null || rpm == null) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(durationInMinutes)
                .multiply(BigDecimal.valueOf(rpm));
    }
}
