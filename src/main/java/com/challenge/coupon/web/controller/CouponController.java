package com.challenge.coupon.web.controller;

import com.challenge.coupon.application.service.CouponService;
import com.challenge.coupon.web.common.ResponseUtils;
import com.challenge.coupon.web.request.CouponRequest;
import com.challenge.coupon.web.response.CouponResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @Operation(
            summary = "Create a new coupon",
            description = "Creates a new coupon applying all business rules and returns the resource location"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Coupon successfully created",
                    headers = @Header(
                            name = "Location",
                            description = "URI of the created coupon",
                            schema = @Schema(type = "string")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data"
            )
    })
    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody CouponRequest dto) {
        CouponResponse coupon = couponService.create(dto);
        return ResponseUtils.created(coupon.getId());
    }

    @Operation(
            summary = "Find coupon by id",
            description = "Returns a coupon by its identifier, if it exists"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Coupon found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CouponResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Coupon not found"
            )
    })
    @GetMapping("/{id}")
    public CouponResponse find(@PathVariable String id) {
        return couponService.find(id);
    }

    @Operation(
            summary = "Delete a coupon",
            description = "Performs a soft delete by changing the coupon status to DELETED"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Coupon successfully deleted"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Coupon not found"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        couponService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
