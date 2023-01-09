### SET 자료형 + Redis Transaction 을 통한 지급 재고 관리

## REDIS CLI
## 127.0.0.1:6379> MULTI
 OK
## 127.0.0.1:6379(TX)> SCARD coupon:time-attack:1:date-time:1:issued:users
 QUEUED
## 127.0.0.1:6379(TX)> SADD coupon:time-attack:1:date-time:1:issued:users 1
 QUEUED
## 127.0.0.1:6379(TX)> EXEC
 1) (integer) 0
 2) (integer) 1
##    127.0.0.1:6379> keys *
 1) "coupon:time-attack:1:date-time:1:issued:users"
## 127.0.0.1:6379> type coupon:time-attack:1:date-time:1:issued:users
   set

## 127.0.0.1:6379> SMEMBERS coupon:time-attack:1:date-time:1:issued:users
 1) "1"

2022071812

- COUPON    (쿠폰)
- USER_INFO (사용자)

coupon:time-attack:1:date-time:1:issued:users 1

1. 쿠폰 재고 확인, 사용자 중복처리 확인 후 쿠폰 지급처리
    - 쿠폰 정책 ID
    - 사용자 ID

2. Kafka Producer 는 DB write / Consumer 는 유저에게 쿠폰 지급 정보를 insert

# RDB
쿠폰 정책 테이블
사용자 테이블

# kafka message
사용자가 소유한 쿠폰
{
   policyId: 1,
   userId: 1
}

# Nosql (Redis)
쿠폰 재고 테이블 > NoSQL (Redis)

