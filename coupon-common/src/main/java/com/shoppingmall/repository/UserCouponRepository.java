package com.shoppingmall.repository;

import com.shoppingmall.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon,String> {

    Optional<UserCoupon> findById(String id);

}
