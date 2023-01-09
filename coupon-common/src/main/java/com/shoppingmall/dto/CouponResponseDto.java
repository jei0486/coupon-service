package com.shoppingmall.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@ToString
@Getter
@Builder
public class CouponResponseDto {
    private String couponName;
    private String startDt;
    private String endDt;
    private char rateYn;
    private Integer discount;
}
