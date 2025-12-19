package com.challenge.coupon.domain.vo;

import com.challenge.coupon.domain.exception.DomainException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CouponCodeTest {

    static Stream<Arguments> provideCodes() {
        return Stream.of(
                Arguments.of("@A.BC-123", "ABC123"),
                Arguments.of("a1b2c3", "A1B2C3"),
                Arguments.of("12-34-56", "123456"),
                Arguments.of("abc@123", "ABC123"),
                Arguments.of("abc@12a", "ABC12A"),
                Arguments.of("abcdef", "ABCDEF"),
                Arguments.of("123456", "123456")
        );
    }

    @ParameterizedTest
    @MethodSource("provideCodes")
    void testValidCodes(String input, String expected) {
        CouponCode couponCode = CouponCode.of(input);
        assertNotNull(couponCode);
        assertEquals(expected, couponCode.getValue());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            "12345",
            "1234567",
            "ABC1235",
            "AB!@#C",
            "aB!@1#2C",
            "ABC",
            "123"
    })
    void testInvalidCodes(String input) {
        assertThrows(DomainException.class, () -> CouponCode.of(input));
    }
}