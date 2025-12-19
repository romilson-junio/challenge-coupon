package com.challenge.coupon.domain.vo;

import com.challenge.coupon.domain.exception.DomainException;
import lombok.Getter;

import java.util.Objects;

@Getter
public final class CouponCode {
    private static final int DEFAULT_LENGTH = 6;
    private final String value;

    private CouponCode(String value) {
        this.value = value;
    }

    public static CouponCode of(String code) {
        try {
            Objects.requireNonNull(code, "Coupon code cannot be null");
        } catch (NullPointerException e) {
            throw new DomainException(e.getMessage(), e);
        }

        String normalized = code
                .replaceAll("[^a-zA-Z0-9]", "")
                .toUpperCase();

        if (normalized.length() != DEFAULT_LENGTH) {
            throw new DomainException(
                    "Coupon code must have exactly " + DEFAULT_LENGTH + " characters"
            );
        }
        return new CouponCode(normalized);
    }
}
