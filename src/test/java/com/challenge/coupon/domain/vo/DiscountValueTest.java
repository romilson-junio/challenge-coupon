package com.challenge.coupon.domain.vo;

import com.challenge.coupon.domain.exception.DomainException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DiscountValueTest {

    @ParameterizedTest
    @NullSource
    @ValueSource(doubles = {
            0.0,
            0.1,
            0.49,
            -1.0
    })
    void testInvalidDiscount(Double input) {
        assertThrows(DomainException.class, () -> DiscountValue.of(input));
    }

    @ParameterizedTest
    @ValueSource(doubles = {
            0.5,
            1.0,
            100.0,
            9999.99
    })
    void testValidDiscount(Double input) {
        DiscountValue discount = DiscountValue.of(input);
        assertNotNull(discount);
        assertEquals(input, discount.getValue());
    }
}