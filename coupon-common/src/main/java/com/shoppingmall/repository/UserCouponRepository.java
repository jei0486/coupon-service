package com.shoppingmall.repository;

import com.shoppingmall.entity.UserCouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserCouponRepository extends JpaRepository<UserCouponEntity,String> {

    Optional<UserCouponEntity> findById(String id);

}
