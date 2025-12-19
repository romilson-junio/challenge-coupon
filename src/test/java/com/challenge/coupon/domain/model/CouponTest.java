package com.challenge.coupon.domain.model;

import com.challenge.coupon.domain.exception.DomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CouponTest {

    static Stream<Object[]> validCouponProvider() {
        return Stream.of(
                new Object[] { "ABC123", "Valid description", 5.0, true},
                new Object[] { "ABC@12.1", "Valid description", 5.0, false},
                new Object[] { "XYZ789", "Another description", 10.5, false},
                new Object[] { "A1B2C3", "Desc with numbers", 0.5, false}
        );
    }

    @ParameterizedTest
    @MethodSource("validCouponProvider")
    void testCreateValidCoupon(String code, String description, double discount, boolean published) {
        LocalDateTime future = LocalDateTime.now().plusDays(1);
        Coupon coupon = Coupon.create(code, description, discount, future, published);

        assertNotNull(coupon.getId());
        assertEquals(code.replaceAll("[^a-zA-Z0-9]", "").toUpperCase(), coupon.getCode().getValue());
        assertEquals(description, coupon.getDescription().getValue());
        assertEquals(discount, coupon.getDiscountValue().getValue());
        assertEquals(future, coupon.getExpirationDate().getValue());
        assertEquals(CouponStatus.ACTIVE, coupon.getStatus());
        assertEquals(published, coupon.isPublished());
        assertFalse(coupon.isRedeemed());
    }

    static Stream<Object[]> invalidCouponProvider() {
        LocalDateTime past = LocalDateTime.now().minusDays(1);
        return Stream.of(
                new Object[] { "ABC", "Valid desc", 5.0, LocalDateTime.now().plusDays(1), "exactly" },
                new Object[] { "ABC123", null, 5.0, LocalDateTime.now().plusDays(1), "cannot be null" },
                new Object[] { "ABC123", "", 5.0, LocalDateTime.now().plusDays(1), "cannot be empty" },
                new Object[] { "ABC123", "Valid desc", 0.1, LocalDateTime.now().plusDays(1), "at least" },
                new Object[] { "ABC123", "Valid desc", 5.0, past, "in the past" }
        );
    }

    @ParameterizedTest
    @MethodSource("invalidCouponProvider")
    void testCreateCouponInvalidFields(String code, String description, Double discount, LocalDateTime expiration, String expectedMessage) {
        DomainException ex = assertThrows(DomainException.class,
                () -> Coupon.create(code, description, discount, expiration, true));
        assertTrue(ex.getMessage().contains(expectedMessage));
    }

    @Test
    void testDeleteCoupon() {
        LocalDateTime future = LocalDateTime.now().plusDays(1);
        Coupon coupon = Coupon.create("ABC123", "Description", 5.0, future, true);

        coupon.delete();
        assertEquals(CouponStatus.DELETED, coupon.getStatus());
    }

    @Test
    void testDeleteAlreadyDeletedCoupon() {
        LocalDateTime future = LocalDateTime.now().plusDays(1);
        Coupon coupon = Coupon.create("ABC123", "Description", 5.0, future, true);
        coupon.delete();

        DomainException ex = assertThrows(DomainException.class, coupon::delete);
        assertEquals("Coupon already deleted", ex.getMessage());
    }

}