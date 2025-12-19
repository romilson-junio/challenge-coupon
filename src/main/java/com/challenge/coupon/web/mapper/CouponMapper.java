package com.challenge.coupon.web.mapper;

import com.challenge.coupon.domain.model.Coupon;
import com.challenge.coupon.web.response.CouponResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CouponMapper {
    @Mapping(target = "code", expression = "java(coupon.getCode().getValue())")
    @Mapping(target = "discountValue", expression = "java(coupon.getDiscountValue().getValue())")
    @Mapping(target = "expirationDate", expression = "java(coupon.getExpirationDate().getValue())")
    @Mapping(target = "description", expression = "java(coupon.getDescription().getValue())")
    CouponResponse response(Coupon coupon);
}
