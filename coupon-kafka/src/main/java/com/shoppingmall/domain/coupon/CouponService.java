package com.shoppingmall.domain.coupon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingmall.entity.Coupon;
import com.shoppingmall.entity.UserCoupon;
import com.shoppingmall.enums.CouponStatus;
import com.shoppingmall.exception.NotExistCouponException;
import com.shoppingmall.repository.CouponRepository;
import com.shoppingmall.repository.UserCouponRepository;
import com.shoppingmall.vo.TimeAttackVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {

    final ObjectMapper mapper;
    final UserCouponRepository userCouponRepository;

    final CouponRepository couponRepository;

/*
Spring에서 컨테이너 팩토리는 KafkaListenerContainerFactory, ConcurrentKafkaListenerContainerFactory 두 가지가 제공됨
전자는 동시성 처리를 지원히지 않고, 후자는 동시성 처리를 지원하기 때문에 후자를 사용할 경우에는
하나의 컨슈머에서 여러개의 파티션을 연결하여 멀티 소비가 가능함
* */

    /*
    * TimeAttackCouponIssue
    *
    * 쿠폰 발행 consume
    *
    * */
    @KafkaListener(containerFactory = "timeAttackCouponKafkaListenerContainerFactory", topics = "${spring.kafka.consumer.timeAttackCouponConsumerConfig.topic}")
    public void TimeAttackCouponIssue(String message) {

        log.info("TimeAttackCouponIssue > TimeAttackVo : {}" , message);
        try {
            TimeAttackVO timeAttackVO = mapper.readValue(message,TimeAttackVO.class);

            log.info("TimeAttackCouponIssue > TimeAttackVo : {}" , String.valueOf(timeAttackVO));

            String id = timeAttackVO.getKey();

            Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new NotExistCouponException("존재하지않는 쿠폰입니다."));

            log.info("TimeAttackCouponIssue > coupon : {}" , String.valueOf(coupon));

            UserCoupon userCoupon = UserCoupon.builder()
                    .id(UUID.randomUUID().toString())
                    .userId(timeAttackVO.getUserId())
                    .status(CouponStatus.PUBLISHED)
                    .issuedAt(LocalDateTime.now())
                    .coupon(coupon)
                    .build();

            log.info("TimeAttackCouponIssue > userCouponEntity : {}",String.valueOf(userCoupon));

            // user_coupon db write
            userCouponRepository.save(userCoupon);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(containerFactory = "notificationExpireCouponKafkaListenerContainerFactory", topics = "${spring.kafka.consumer.notificationExpireCouponConsumerConfig.topic}")
    public void NotificationExpireCoupon(ConsumerRecord<String, String> consumerRecord) {
        log.info("NotificationExpireCoupon > ConsumerRecord : {}" , String.valueOf(consumerRecord));
    }

}
