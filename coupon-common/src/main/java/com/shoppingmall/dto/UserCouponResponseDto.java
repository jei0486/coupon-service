package com.shoppingmall.dto;

import com.shoppingmall.entity.UserCouponEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@Data
public class UserCouponResponseDto {

    private String user_id;
    private String c_name;
    private Integer discount;
    private char rate_yn;

    private LocalDateTime start_dt;
    private LocalDateTime end_dt;

    private LocalDateTime issued_at;
    private LocalDateTime used_at;



}
