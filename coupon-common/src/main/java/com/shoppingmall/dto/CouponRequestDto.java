package com.shoppingmall.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@ToString
@Builder
public class CouponRequestDto {

    @NotBlank(message = "쿠폰 이름을 입력하세요.")
    @Size(max = 255, message = "쿠폰명 사이즈를 초과하였습니다.")
    private String c_name;

    @NotNull(message = "쿠폰 할인율을 작성하세요.")
    private Integer discount;

    @NotBlank(message = "할인 시작 기간을 입력하세요.")
    private String start_dt;

    @NotBlank(message = "할인 종료 기간을 입력하세요.")
    private String end_dt;

    @NotBlank(message = "할인율/할인가격 여부를 입력하세요.")
    private char rate_yn;

}
