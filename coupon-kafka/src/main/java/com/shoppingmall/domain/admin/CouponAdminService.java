package com.shoppingmall.domain.admin;

import com.shoppingmall.entity.Coupon;
import com.shoppingmall.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponAdminService {

    private final CouponRepository couponRepository;

    public void createCoupon(Coupon coupon){
        couponRepository.save(coupon);
    }
}
