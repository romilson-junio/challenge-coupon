package com.challenge.coupon.infrastructure.persistence.mapper;

import com.challenge.coupon.domain.model.Coupon;
import com.challenge.coupon.infrastructure.persistence.entity.CouponEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring")
public interface CouponEntityMapper {
    @Mapping(target = "code", expression = "java(domain.getCode().getValue())")
    @Mapping(target = "description", expression = "java(domain.getDescription().getValue())")
    @Mapping(target = "discountValue", expression = "java(domain.getDiscountValue().getValue())")
    @Mapping(target = "expirationDate", expression = "java(domain.getExpirationDate().getValue())")
    CouponEntity entity(Coupon domain);

    @ObjectFactory
    default Coupon restore(CouponEntity entity) {
        return Coupon.restore(
                entity.getId(),
                entity.getCode(),
                entity.getDescription(),
                entity.getDiscountValue(),
                entity.getExpirationDate(),
                entity.getStatus(),
                entity.isPublished(),
                entity.isRedeemed()
        );
    }
    Coupon domain(CouponEntity entity);
}
