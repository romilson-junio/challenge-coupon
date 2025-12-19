package com.challenge.coupon.application.port;

import com.challenge.coupon.domain.model.Coupon;

import java.util.Optional;

public interface CouponRepositoryPort {
    Coupon save(Coupon coupon);
    Optional<Coupon> findById(String id);
}
