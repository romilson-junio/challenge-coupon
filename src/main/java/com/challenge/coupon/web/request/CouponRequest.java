package com.challenge.coupon.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CouponRequest {

    @Schema(
            description = "obrigatório",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String code;

    @Schema(description = "obrigatório",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String description;

    @Schema(description = "obrigatório",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Double discountValue;

    @Schema(description = "obrigatório",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalDateTime expirationDate;

    private boolean published;
}
