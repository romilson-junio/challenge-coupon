package com.challenge.coupon.application.service.impl;

import com.challenge.coupon.application.port.CouponRepositoryPort;
import com.challenge.coupon.application.service.CouponService;
import com.challenge.coupon.domain.exception.CouponNotFoundException;
import com.challenge.coupon.domain.model.Coupon;
import com.challenge.coupon.web.mapper.CouponMapper;
import com.challenge.coupon.web.request.CouponRequest;
import com.challenge.coupon.web.response.CouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepositoryPort couponRepositoryPort;
    private final CouponMapper couponMapper;

    @Override
    public CouponResponse create(CouponRequest request) {
        Coupon coupon = Coupon.create(
                request.getCode(),
                request.getDescription(),
                request.getDiscountValue(),
                request.getExpirationDate(),
                request.isPublished());
        return couponMapper.response(couponRepositoryPort.save(coupon));
    }

    @Override
    public CouponResponse find(String id) {
        Coupon coupon = couponRepositoryPort.findById(id)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found: " + id));
        return couponMapper.response(coupon);
    }

    @Override
    public void delete(String id) {
        Coupon coupon = couponRepositoryPort.findById(id)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found: " + id));

        coupon.delete();
        couponRepositoryPort.save(coupon);
    }
}
