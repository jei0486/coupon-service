package com.shoppingmall.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserCouponResponseDto {

    private String userId;
    private String couponName;
    private Integer discount;
    private char rateYn;
    private LocalDateTime startDt;
    private LocalDateTime endDt;
    private LocalDateTime issuedAt;
    private LocalDateTime usedAt;

}
