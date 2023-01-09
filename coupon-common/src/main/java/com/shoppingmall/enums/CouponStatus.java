package com.shoppingmall.enums;

public enum CouponStatus {
    PUBLISHED("발행"), USED("사용완료"), EXPIRED("만료");

    private String status;

    CouponStatus(String status){this.status = status;}

    public String getValue(){return status;}

}
