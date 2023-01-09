package com.shoppingmall.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoppingmall.entity.UserCoupon;
import com.shoppingmall.enums.CouponStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.shoppingmall.entity.QCoupon.coupon;
import static com.shoppingmall.entity.QUserCoupon.userCoupon;


@RequiredArgsConstructor
@Repository
public class UserCouponCustomRepositoryImpl implements UserCouponCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<UserCoupon> findAllByStatusAndEndDtBetween(CouponStatus status, LocalDateTime startDate, LocalDateTime endDate){
        return jpaQueryFactory.select(userCoupon)
                .from(userCoupon)
                .join(coupon)
                    .on(coupon.id.eq(userCoupon.coupon.id))
                .where(userCoupon.status.eq(status),
                        coupon.endDt.between(startDate,endDate)
                        )
                .fetch();
    }

}
