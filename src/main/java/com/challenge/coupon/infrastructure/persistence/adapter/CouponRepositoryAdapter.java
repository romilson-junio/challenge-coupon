package com.challenge.coupon.infrastructure.persistence.adapter;

import com.challenge.coupon.application.port.CouponRepositoryPort;
import com.challenge.coupon.domain.model.Coupon;
import com.challenge.coupon.infrastructure.persistence.mapper.CouponEntityMapper;
import com.challenge.coupon.infrastructure.persistence.entity.CouponEntity;
import com.challenge.coupon.infrastructure.persistence.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CouponRepositoryAdapter implements CouponRepositoryPort {

    private final CouponRepository couponRepository;
    private final CouponEntityMapper couponEntityMapper;

    @Override
    public Coupon save(Coupon coupon) {
        CouponEntity entity = couponEntityMapper.entity(coupon);
        return couponEntityMapper.domain(couponRepository.save(entity));
    }

    @Override
    public Optional<Coupon> findById(String id) {
        return couponRepository.findById(UUID.fromString(id))
                .map(couponEntityMapper::domain);
    }
}
