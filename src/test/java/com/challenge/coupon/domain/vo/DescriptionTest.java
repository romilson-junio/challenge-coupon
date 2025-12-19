package com.challenge.coupon.domain.vo;

import com.challenge.coupon.domain.exception.DomainException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DescriptionTest {

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            ""
    })
    void testInvalidDescription(String input) {
        assertThrows(DomainException.class, () -> Description.of(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Short description"
    })
    void testValidDescription(String input) {
        Description description = Description.of(input);
        assertNotNull(description);
        assertEquals(input, description.getValue());
    }
}