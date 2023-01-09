package com.shoppingmall.domain.admin;

import com.shoppingmall.dto.CouponRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public class AdminController {

    //   @ApiOperation(value = "쿠폰 생성 (관리자)")
    @PostMapping(value = "/admin/coupon/create")
    public ResponseEntity<String> couponCreate(@RequestBody @Valid CouponRequestDto couponRequestDto,
                                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        //couponService.couponCreate(couponRequestDto);

        return ResponseEntity.ok().body("쿠폰정보가 생성되었습니다.");
    }

    // @ApiOperation(value = "쿠폰 수정 (관리자)")
    @PutMapping(value = "/admin/coupon/update/{couponId}")
    public ResponseEntity<String>  couponUpdate(@RequestBody @Valid CouponRequestDto couponRequestDto,
                                                @PathVariable("couponId") String couponId,
                                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        //couponService.couponUpdate(couponRequestDto);
        return ResponseEntity.ok().body("쿠폰정보가 수정되었습니다.");
    }

    // @ApiOperation(value = "쿠폰 삭제 (관리자)")
    @DeleteMapping(value = "/admin/coupon/delete/{couponId}")
    public ResponseEntity<String> couponDelete(@PathVariable("couponId") String couponId){

        //couponService.couponDelete(couponRequestDto);
        return ResponseEntity.ok().body("쿠폰정보가 삭제되었습니다.");
    }
}
