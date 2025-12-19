package com.challenge.coupon.application.service.impl;

import com.challenge.coupon.application.port.CouponRepositoryPort;
import com.challenge.coupon.application.service.CouponService;
import com.challenge.coupon.domain.exception.CouponNotFoundException;
import com.challenge.coupon.domain.exception.DomainException;
import com.challenge.coupon.domain.model.Coupon;
import com.challenge.coupon.domain.model.CouponStatus;
import com.challenge.coupon.web.mapper.CouponMapper;
import com.challenge.coupon.web.mapper.CouponMapperImpl;
import com.challenge.coupon.web.request.CouponRequest;
import com.challenge.coupon.web.response.CouponResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {
        CouponServiceImpl.class,
        CouponRepositoryPort.class,
        CouponMapperImpl.class
})
class CouponServiceImplTest {

    @Autowired private CouponService couponService;

    @MockitoBean private CouponRepositoryPort couponRepositoryPort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testCreateCoupon_Success(boolean published) {
        CouponRequest request = new CouponRequest();
        request.setCode("ABC123");
        request.setDescription("Valid description");
        request.setDiscountValue(5.0);
        request.setExpirationDate(LocalDateTime.now().plusDays(1));
        request.setPublished(published);

        Coupon coupon = Coupon.create(
                request.getCode(),
                request.getDescription(),
                request.getDiscountValue(),
                request.getExpirationDate(),
                request.isPublished()
        );
        when(couponRepositoryPort.save(any(Coupon.class))).thenReturn(coupon);

        CouponResponse result = couponService.create(request);

        assertNotNull(result);
        assertEquals(coupon.getId().toString(), result.getId());
        assertEquals(published, result.isPublished());
        verify(couponRepositoryPort).save(any(Coupon.class));
    }

    @Test
    void testFindCoupon_Success() {
        UUID id = UUID.randomUUID();
        Coupon coupon = Coupon.create("ABC123", "Desc", 5.0, LocalDateTime.now().plusDays(1), true);

        when(couponRepositoryPort.findById(id.toString())).thenReturn(Optional.of(coupon));

        CouponResponse result = couponService.find(id.toString());

        assertNotNull(result);
        assertEquals(coupon.getId().toString(), result.getId());
    }

    @Test
    void testFindCoupon_NotFound() {
        String id = UUID.randomUUID().toString();
        when(couponRepositoryPort.findById(id)).thenReturn(Optional.empty());

        CouponNotFoundException ex = assertThrows(CouponNotFoundException.class, () -> couponService.find(id));
        assertEquals("Coupon not found: " + id, ex.getMessage());
    }

    @Test
    void testDeleteCoupon_Success() {
        String id = UUID.randomUUID().toString();
        Coupon coupon = Coupon.create("ABC123", "Desc", 5.0, LocalDateTime.now().plusDays(1), true);

        when(couponRepositoryPort.findById(id)).thenReturn(Optional.of(coupon));
        when(couponRepositoryPort.save(coupon)).thenReturn(coupon);

        assertDoesNotThrow(() -> couponService.delete(id));
        assertEquals(CouponStatus.DELETED, coupon.getStatus());
        verify(couponRepositoryPort).save(coupon);
    }

    @Test
    void testDeleteCoupon_NotFound() {
        String id = UUID.randomUUID().toString();
        when(couponRepositoryPort.findById(id)).thenReturn(Optional.empty());

        CouponNotFoundException ex = assertThrows(CouponNotFoundException.class, () -> couponService.delete(id));
        assertEquals("Coupon not found: " + id, ex.getMessage());
    }

    @Test
    void testDeleteCoupon_AlreadyDeleted() {
        String id = UUID.randomUUID().toString();
        Coupon coupon = Coupon.create("ABC123", "Desc", 5.0, LocalDateTime.now().plusDays(1), true);
        coupon.delete();

        when(couponRepositoryPort.findById(id)).thenReturn(Optional.of(coupon));

        DomainException ex = assertThrows(DomainException.class, () -> couponService.delete(id));
        assertEquals("Coupon already deleted", ex.getMessage());
    }
}