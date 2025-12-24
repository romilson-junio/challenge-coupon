package com.challenge.coupon.domain.model;

import com.challenge.coupon.domain.exception.DomainException;
import com.challenge.coupon.domain.vo.CouponCode;
import com.challenge.coupon.domain.vo.Description;
import com.challenge.coupon.domain.vo.DiscountValue;
import com.challenge.coupon.domain.vo.ExpirationDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Coupon {

    private final UUID id;
    private final CouponCode code;
    private final Description description;
    private final DiscountValue discountValue;
    private final ExpirationDate expirationDate;
    private CouponStatus status;

    private boolean published;
    private boolean redeemed;

    public static Coupon create(
            String code,
            String description,
            Double discount,
            LocalDateTime expiration,
            boolean published
    ) {

        return new Coupon(
                UUID.randomUUID(),
                CouponCode.of(code),
                Description.of(description),
                DiscountValue.of(discount),
                ExpirationDate.of(expiration),
                CouponStatus.ACTIVE,
                published,
                false);
    }

    public static Coupon restore(
            UUID id,
            String code,
            String description,
            Double discount,
            LocalDateTime expiration,
            CouponStatus status,
            boolean published,
            boolean redeemed
    ) {
        return new Coupon(
                id,
                CouponCode.of(code),
                Description.of(description),
                DiscountValue.of(discount),
                ExpirationDate.of(expiration),
                status,
                published,
                redeemed
        );
    }

    public void delete() {
        if (this.status == CouponStatus.DELETED) {
            throw new DomainException("Coupon already deleted");
        }
        this.status = CouponStatus.DELETED;
    }
}
