package com.shoppingmall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shoppingmall.dto.UserCouponResponseDto;
import com.shoppingmall.enums.CouponStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="user_coupon")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponEntity {

    @Id
    private String id;
    private String userId;
    private LocalDateTime issuedAt;
    private LocalDateTime usedAt;
    @Enumerated(value = EnumType.STRING)
    private CouponStatus status;

    @JsonIgnore
    @ManyToOne(targetEntity = CouponEntity.class , fetch = FetchType.LAZY)
    @JoinColumn(name = "POLICY_ID")
    private CouponEntity coupon;

    public UserCouponResponseDto toResponseDto(){
        return UserCouponResponseDto.builder()
                .userId(userId)
                .issuedAt(issuedAt)
                .couponName(coupon.getCouponName())
                .discount(coupon.getDiscount())
                .startDt(coupon.getStartDt())
                .endDt(coupon.getEndDt())
                .build();
    }


}
