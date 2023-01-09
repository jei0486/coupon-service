package com.shoppingmall.batch;

import com.shoppingmall.domain.coupon.CouponService;
import com.shoppingmall.dto.UserCouponResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.batch.JobExecutionEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationJob {

    private final CouponService couponService;

    /*
    *
    * 크론식 정리

    // 5초마다 실행
    @Scheduled(fixedDelay = 5 * 1000L)

    // 1 분마다
    cron = "0 * * * * *"

    * */

    // 쿠폰 만료 하루전 알림 - use kafka
    //@Scheduled(cron = "0 */10 * * * *") // 10분 주기로 실행
    public void job_1(){

        List<UserCouponResponseDto> userCouponResponseDtoList = couponService.findAllByEndDateBetweenToday();

        // coupon notification publish
        couponService.publishNotification(userCouponResponseDtoList);

    }

    // 쿠폰 만료 하루전 알림 - non kafka
    //@Scheduled(cron = "0 */10 * * * *")
    public void job_2(){
        List<UserCouponResponseDto> userCouponResponseDtoList = couponService.findAllByEndDateBetweenToday();


    }
}
