package com.challenge.coupon.web.common;

import com.challenge.coupon.application.exception.ApplicationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@UtilityClass
public class ResponseUtils {
    public ResponseEntity<Void> created(String resourceId) {
        if (resourceId == null || resourceId.isBlank()) {
            throw new ApplicationException("Resource ID cannot be null or blank");
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resourceId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    public static String getFullURL(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();

        return  (queryString == null)
                ? requestURL.toString()
                : requestURL.append('?').append(queryString).toString();
    }
}
