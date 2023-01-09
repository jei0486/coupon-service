package com.shoppingmall.domain.admin;

import com.shoppingmall.entity.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin/coupon")
@RequiredArgsConstructor
@RestController
public class CouponAdminController {

    private final CouponAdminService couponService;

    // 쿠폰 생성
    @PostMapping("")
    private void create(Coupon coupon){
        couponService.createCoupon(coupon);
    }

}
