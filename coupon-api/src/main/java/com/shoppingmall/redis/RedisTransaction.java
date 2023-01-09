package com.shoppingmall.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RedisTransaction {

/*
Redis Transaction의 경우 기존 RDB Transaction과 다르게
Rollback이 불가능한 케이스가 존재하기에,
이 부분은 별도 Rollback 로직을 구현해야 합니다.

단, Cluster Redis 환경에서는 Transaction이 지원되지 않습니다.
(Standalone Redis에서만 Transaction 지원 가능합니다..)
(Redis Sentinel : Transaction 지원)

*** Redis Operation (운영모드)
    - Standalone (single instance)
    - Sentinel
    - Cluster

* */

    public Object execute(
            RedisOperations<String, Object> redisTemplate, RedisOperation operation, Object vo) {

        return redisTemplate.execute(
                new SessionCallback<Object>() {
                    @Override
                    public Object execute(RedisOperations callbackOperations) throws DataAccessException {

                        // [1] REDIS 트랜잭션 Start
                        callbackOperations.multi();

                        // [2] Operation 실행
                        operation.execute(callbackOperations, vo);

                        // [3] REDIS 트랜잭션 End
                        return callbackOperations.exec();
                    }
                });
    }

}