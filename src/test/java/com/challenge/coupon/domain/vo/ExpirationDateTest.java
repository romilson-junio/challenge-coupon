package com.challenge.coupon.domain.vo;

import com.challenge.coupon.domain.exception.DomainException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ExpirationDateTest {

    @ParameterizedTest
    @NullSource
    void testNullDate(LocalDateTime input) {
        assertThrows(DomainException.class, () -> ExpirationDate.of(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2000-01-01T00:00",
            "2020-12-31T23:59",
            "2023-01-01T12:00"
    })
    void testPastDates(String input) {
        LocalDateTime past = LocalDateTime.parse(input);
        assertThrows(DomainException.class, () -> ExpirationDate.of(past));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2099-12-31T23:59",
            "2050-01-01T12:00",
            "2100-06-15T08:30"
    })
    void testFutureDates(String input) {
        LocalDateTime future = LocalDateTime.parse(input);
        ExpirationDate expiration = ExpirationDate.of(future);
        assertEquals(future, expiration.getValue());
    }

}