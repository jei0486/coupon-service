package com.shoppingmall.domain.coupon;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.shoppingmall.dto.TimeAttackRequestDto;
import com.shoppingmall.dto.UserCouponResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CouponController {

    final CouponService couponService;

//    @ApiOperation(value = "쿠폰 발급 (고객)")
    @PostMapping(value = "/coupon/issued")
    public  ResponseEntity<String> couponIssued(@RequestBody @Valid TimeAttackRequestDto dto,
                                                BindingResult bindingResult) throws JsonProcessingException {

        log.info("coupon issue > {}",String.valueOf(dto));

        if (bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().body(couponService.couponIssued(dto));
    }


    //@ApiOperation(value = "쿠폰 List")
//    @GetMapping(value = "/coupons")
//    public ResponseEntity<?> getCoupon(){
//        return ResponseEntity.ok().body(couponService.getCoupon());
//    }

    //    @ApiOperation(value = "쿠폰 발급 (고객) - 카프카를 사용하지않음")
    @PostMapping(value = "/coupon/issued/noKafka")
    public  ResponseEntity<String> couponIssuedNoneKafka(@RequestBody @Valid TimeAttackRequestDto dto,
                                                BindingResult bindingResult) {

        log.info("coupon issue > {}",String.valueOf(dto));

        if (bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().body(couponService.couponIssuedNoneKafka(dto));
    }

    /*
    * 테스트
    * */
//    @GetMapping(value = "/test")
//    public ResponseEntity<?> test(){
//
//        return ResponseEntity.ok().body(couponService.findAllByEndDateBetweenToday());
//    }




}
