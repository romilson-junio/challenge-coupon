package com.challenge.coupon.web.response;

import com.challenge.coupon.domain.model.CouponStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CouponResponse {
    private String id;
    private String code;
    private String description;
    private Double discountValue;
    private LocalDateTime expirationDate;
    private CouponStatus status;
    private boolean published;
    private boolean redeemed;
}
