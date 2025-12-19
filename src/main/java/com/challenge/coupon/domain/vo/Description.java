package com.challenge.coupon.domain.vo;

import com.challenge.coupon.domain.exception.DomainException;
import lombok.Getter;

import java.util.Objects;

@Getter
public final class Description {
    private final String value;

    private Description(String description) {
        this.value = description;
    }

    public static Description of(String description) {
        try {
            Objects.requireNonNull(description, "Description cannot be null");
            if (description.isBlank()) {
                throw new DomainException("Description cannot be empty");
            }
        } catch (NullPointerException e) {
            throw new DomainException(e.getMessage(), e);
        }

        return new Description(description);
    }
}
