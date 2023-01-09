package com.shoppingmall.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="coupon_info")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {


    @Id
    @Column(name="coupon_info_id")
    private String id;      // UUID
    private String couponName; // 쿠폰 이름
    private int discount; // 할인율 혹은 할인되는 가격
    private LocalDateTime createdAt;
    // 세일 시작 시간 ~ 끝나는 시간
    private LocalDateTime startDt;
    private LocalDateTime endDt;
    private char rateYn; // 할인율 혹은 할인 가격 여부

}
