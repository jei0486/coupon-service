package com.shoppingmall.repository;

import com.shoppingmall.entity.UserCoupon;
import com.shoppingmall.enums.CouponStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface UserCouponCustomRepository {

    List<UserCoupon> findAllByStatusAndEndDtBetween(CouponStatus status, LocalDateTime startDate, LocalDateTime endDate);
}
