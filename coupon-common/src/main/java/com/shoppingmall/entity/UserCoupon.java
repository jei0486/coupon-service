package com.shoppingmall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class UserCoupon {

    @Id
    @Column(name="user_coupon_id")
    private String id;
    private String userId;
    private LocalDateTime issuedAt;
    private LocalDateTime usedAt;

    @Enumerated(value = EnumType.STRING)
    private CouponStatus status;


    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Coupon.class)
    @JoinColumn(name = "coupon_info_id")
    private Coupon coupon;

}
