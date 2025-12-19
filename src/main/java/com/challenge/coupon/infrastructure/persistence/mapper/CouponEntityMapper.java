package com.challenge.coupon.infrastructure.persistence.mapper;

import com.challenge.coupon.domain.model.Coupon;
import com.challenge.coupon.infrastructure.persistence.entity.CouponEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CouponEntityMapper {
    @Mapping(target = "code", expression = "java(domain.getCode().getValue())")
    @Mapping(target = "description", expression = "java(domain.getDescription().getValue())")
    @Mapping(target = "discountValue", expression = "java(domain.getDiscountValue().getValue())")
    @Mapping(target = "expirationDate", expression = "java(domain.getExpirationDate().getValue())")
    CouponEntity entity(Coupon domain);

    @Mapping(target = "code", expression = "java(CouponCode.of(entity.getCode()))")
    @Mapping(target = "description", expression = "java(Description.of(entity.getDescription()))")
    @Mapping(target = "discountValue", expression = "java(DiscountValue.of(entity.getDiscountValue()))")
    @Mapping(target = "expirationDate", expression = "java(ExpirationDate.of(entity.getExpirationDate()))")
    Coupon domain(CouponEntity entity);
}
