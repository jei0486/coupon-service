package com.shoppingmall.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingmall.dto.CouponRequestDto;
import com.shoppingmall.dto.CouponResponseDto;
import com.shoppingmall.dto.UserCouponResponseDto;
import com.shoppingmall.entity.CouponEntity;
import com.shoppingmall.entity.UserCouponEntity;
import com.shoppingmall.enums.CouponStatus;
import com.shoppingmall.exception.NotExistCouponException;
import com.shoppingmall.repository.CouponRepository;
import com.shoppingmall.repository.UserCouponRepository;
import com.shoppingmall.vo.TimeAttackVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

            CouponEntity coupon = couponRepository.findById(id)
                .orElseThrow(() -> new NotExistCouponException("존재하지않는 쿠폰입니다."));

            log.info("TimeAttackCouponIssue > coupon : {}" , String.valueOf(coupon));

            UserCouponEntity userCouponEntity = UserCouponEntity.builder()
                    .id(UUID.randomUUID().toString())
                    .user_id(timeAttackVO.getUser_id())
                    .coupon(coupon)
                    .issued_at(LocalDateTime.now())
                    .build();

            log.info("TimeAttackCouponIssue > userCouponEntity : {}",String.valueOf(userCouponEntity));

            // user_coupon db write
            userCouponRepository.save(userCouponEntity);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(containerFactory = "notificationExpireCouponKafkaListenerContainerFactory", topics = "${spring.kafka.consumer.notificationExpireCouponConsumerConfig.topic}")
    public void NotificationExpireCoupon(ConsumerRecord<String, String> consumerRecord) {
        log.info("NotificationExpireCoupon > ConsumerRecord : {}" , String.valueOf(consumerRecord));
    }

}
