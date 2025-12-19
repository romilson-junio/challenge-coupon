package com.challenge.coupon.web.handler.error;

import com.challenge.coupon.web.common.ResponseUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class StandardError {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandardError(HttpStatus status, String message, HttpServletRequest request) {
        super();
        this.date = LocalDateTime.now();
        this.status = status.value();
        this.error = status.name();
        this.message = message;
        this.path = ResponseUtils.getFullURL(request);
    }
}
