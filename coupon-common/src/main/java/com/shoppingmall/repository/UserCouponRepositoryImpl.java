package com.shoppingmall.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoppingmall.entity.UserCoupon;
import com.shoppingmall.enums.CouponStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


@RequiredArgsConstructor
@Repository
public class UserCouponRepositoryImpl  {

    private final JPAQueryFactory jpaQueryFactory;

//    public List<UserCoupon> findAllByStatusAndEndDtBetween(CouponStatus status, LocalDateTime startDate, LocalDateTime endDate){
//        return jpaQueryFactory.select(userCoupon)
//                .from(userCoupon)
//                .join(coupon)
//                    //.on(coupon.id.eq(userCoupon.policyId))
//                    .on(coupon.id.eq(userCoupon.userId))
//                .where(userCoupon.status.eq(status),
//                        coupon.endDt.between(startDate,endDate)
//                        )
//                .fetch();
//    }

}
