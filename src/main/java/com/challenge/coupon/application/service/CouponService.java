package com.challenge.coupon.application.service;

import com.challenge.coupon.web.request.CouponRequest;
import com.challenge.coupon.web.response.CouponResponse;
import org.springframework.stereotype.Service;

@Service
public interface CouponService {
    CouponResponse create(CouponRequest coupon);
    CouponResponse find(String id);
    void delete(String id);
}
