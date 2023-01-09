package com.shoppingmall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shoppingmall.dto.UserCouponResponseDto;
import com.shoppingmall.enums.CouponStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="USER_COUPON")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponEntity {

    @Id
    private String id;
    private String user_id;
    private LocalDateTime issued_at;
    private LocalDateTime used_at;

    @Enumerated(value = EnumType.STRING)
    private CouponStatus status;

    @JsonIgnore
    @ManyToOne(targetEntity = CouponEntity.class , fetch = FetchType.LAZY)
    @JoinColumn(name = "POLICY_ID")
    private CouponEntity coupon;

    public UserCouponResponseDto toResponseDto(){
        return UserCouponResponseDto.builder()
                .user_id(user_id)
                .issued_at(issued_at)
                .c_name(coupon.getC_name())
                .discount(coupon.getDiscount())
                .start_dt(coupon.getStartDt())
                .end_dt(coupon.getEndDt())
                .build();
    }


}
