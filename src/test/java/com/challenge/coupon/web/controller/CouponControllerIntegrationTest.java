package com.challenge.coupon.web.controller;

import com.challenge.coupon.CouponApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = CouponApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CouponControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateFindDeleteCoupon() throws Exception {

        String requestJson = """
            {
                "code": "ABC123",
                "description": "Integration test coupon",
                "discountValue": 5.0,
                "expirationDate": "2030-12-31T12:00:00",
                "published": true
            }
        """;

        String location = mockMvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn()
                .getResponse()
                .getHeader("Location");

        String id = location.substring(location.lastIndexOf("/") + 1);

        mockMvc.perform(get("/coupon/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.code").value("ABC123"));

        mockMvc.perform(delete("/coupon/" + id))
                .andExpect(status().isNoContent());
    }

    private static Stream<Object[]> invalidCouponProvider() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime past = now.minusDays(1);
        LocalDateTime future = now.plusDays(1);

        return Stream.of(
                new Object[]{"", "Valid description", 5.0, future},
                new Object[]{"ABC@1234", "Valid description", 5.0, future},
                new Object[]{"ABC@12", "Valid description", 5.0, future},
                new Object[]{"ABC123", "", 5.0, future},
                new Object[]{"ABC123", "Valid description", 0.0, future},
                new Object[]{"ABC123", "Valid description", 5.0, past}
        );
    }

    @ParameterizedTest
    @MethodSource("invalidCouponProvider")
    void testCreateCoupon_InvalidRequest_ShouldReturn400(
            String code,
            String description,
            Double discountValue,
            LocalDateTime expirationDate
    ) throws Exception {
        String requestJson = String.format("""
            {
                "code": "%s",
                "description": "%s",
                "discountValue": %s,
                "expirationDate": "%s",
                "published": true
            }
        """, code, description, discountValue, expirationDate);

        mockMvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFindCoupon_NotFound_ShouldReturn404() throws Exception {
        String nonExistentId = "11111111-1111-1111-1111-111111111111";

        mockMvc.perform(get("/coupon/" + nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Coupon not found: " +  nonExistentId));
    }
}
