package com.challenge.coupon.domain.vo;

import com.challenge.coupon.domain.exception.DomainException;
import lombok.Getter;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public final class ExpirationDate {
    private final LocalDateTime value;

    private ExpirationDate(LocalDateTime date) {
        this.value = date;
    }

    public static ExpirationDate of(LocalDateTime date) {
        try {
            Objects.requireNonNull(date, "Expiration date cannot be null");
        } catch (NullPointerException e) {
            throw new DomainException(e.getMessage(), e);
        }

        LocalDateTime now = LocalDateTime.now(Clock.systemDefaultZone());
        if (date.isBefore(now)) {
            throw new DomainException(
                    "Expiration date cannot be in the past"
            );
        }
        return new ExpirationDate(date);
    }
}
