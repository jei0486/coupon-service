package com.shoppingmall.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class CouponResponseDto {

    private String c_name;
    private Integer discount;
    private String start_dt;
    private String end_dt;
    private char rate_yn;
}
