package com.challenge.coupon.web.common;

import com.challenge.coupon.application.exception.ApplicationException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResponseUtilsTest {

    @BeforeEach
    void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/coupons");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void testCreated_withValidId() {
        ResponseEntity<Void> response = ResponseUtils.created("123");

        assertNotNull(response);
        assertEquals(201, response.getStatusCode().value());

        URI location = response.getHeaders().getLocation();
        assertNotNull(location);
        assertTrue(location.toString().endsWith("/123"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testCreated_withNullOrEmptyId_throwsException(String resourceId) {
        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> ResponseUtils.created(resourceId)
        );
        assertEquals("Resource ID cannot be null or blank", exception.getMessage());
    }

    @Test
    void testGetFullURL_noQueryString() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/test"));
        when(request.getQueryString()).thenReturn(null);

        String fullURL = ResponseUtils.getFullURL(request);
        assertEquals("http://localhost:8080/test", fullURL);
    }

    @Test
    void testGetFullURL_withQueryString() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/test"));
        when(request.getQueryString()).thenReturn("param1=value1&param2=value2");

        String fullURL = ResponseUtils.getFullURL(request);
        assertEquals("http://localhost:8080/test?param1=value1&param2=value2", fullURL);
    }
}