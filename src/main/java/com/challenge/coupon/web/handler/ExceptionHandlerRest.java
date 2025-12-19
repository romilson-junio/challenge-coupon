package com.challenge.coupon.web.handler;

import com.challenge.coupon.application.exception.ApplicationException;
import com.challenge.coupon.domain.exception.CouponNotFoundException;
import com.challenge.coupon.domain.exception.DomainException;
import com.challenge.coupon.web.handler.error.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerRest {

    @ExceptionHandler({ DomainException.class })
    public ResponseEntity<StandardError> errorResponse(DomainException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST, e.getMessage(), request);
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler({ ApplicationException.class })
    public ResponseEntity<StandardError> errorResponse(ApplicationException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), request);
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<StandardError> handleCouponNotFound(CouponNotFoundException e,
                                                              HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND, e.getMessage(), request);
        return ResponseEntity.status(err.getStatus()).body(err);
    }

}
