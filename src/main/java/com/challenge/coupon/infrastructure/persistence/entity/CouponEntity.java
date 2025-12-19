package com.challenge.coupon.infrastructure.persistence.entity;

import com.challenge.coupon.domain.model.CouponStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponEntity {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String code;

    @Column(length = 500)
    private String description;

    @Column(name = "discount_value")
    private Double discountValue;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Enumerated(EnumType.STRING)
    private CouponStatus status;

    private boolean published;
    private boolean redeemed;
}
