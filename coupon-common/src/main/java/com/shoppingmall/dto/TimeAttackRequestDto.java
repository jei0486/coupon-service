package com.shoppingmall.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@Builder
public class TimeAttackRequestDto {

    @NotBlank(message = "쿠폰정책을 입력하세요.")
    String key;
    @NotBlank(message = "사용자 이름을 입력하세요.")
    String userId;
}
