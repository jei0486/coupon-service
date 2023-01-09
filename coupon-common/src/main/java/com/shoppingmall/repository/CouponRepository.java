package com.shoppingmall.repository;

import com.shoppingmall.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CouponRepository extends JpaRepository<CouponEntity,String> {

    Optional<CouponEntity> findById(String id);


}
