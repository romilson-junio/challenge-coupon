package com.challenge.coupon.domain.vo;

import com.challenge.coupon.domain.exception.DomainException;
import lombok.Getter;

import java.util.Objects;

@Getter
public final class DiscountValue {
    private static final Double MIN_VALUE = 0.5;
    private final Double value;

    private DiscountValue(Double discount) {
        this.value = discount;
    }

    public static DiscountValue of(Double discount) {
        try {
            Objects.requireNonNull(discount, "Discount value cannot be null");
        } catch (NullPointerException e) {
            throw new DomainException(e.getMessage(), e);
        }

        if (discount.compareTo(MIN_VALUE) < 0) {
            throw new DomainException(
                    "Discount value must be at least " + MIN_VALUE
            );
        }

        return new DiscountValue(discount);
    }
}
