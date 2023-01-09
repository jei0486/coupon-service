package com.shoppingmall.domain;

import com.shoppingmall.redis.RedisOperation;
import com.shoppingmall.vo.TimeAttackVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class TimeAttackOperation implements RedisOperation<TimeAttackVO> {
/*
-- Kafka Topic 정보
Topic 명 : TimeAttackCouponIssue

Body 정보 :
{
 policyId: 1,
 userId: 1,
 issuedAt: "2022–07–13T11:31:42"
}
* */
    @Override
    public Long count(RedisOperations<String, Object> operations, TimeAttackVO vo) {
        String key = vo.getKey();
        Long size = operations.opsForSet().size(key);
        log.debug("[TimeAttackOperation] [count] key ::: {}, size ::: {}", key, size);
        return size;
    }

    @Override
    public Long add(RedisOperations<String, Object> operations, TimeAttackVO vo) {
        String key = vo.getKey();
        String value = this.generateValue(vo);
        Long result = operations.opsForSet().add(key, value);
        log.debug(
                "[TimeAttackOperation] [add] key ::: {}, value ::: {}, result ::: {}", key, value, result);
        return result;
    }

    @Override
    public Long remove(RedisOperations<String, Object> operations, TimeAttackVO vo) {
        String key = vo.getKey();
        String value = this.generateValue(vo);
        Long result = operations.opsForSet().remove(key, value);

        log.debug(
                "[TimeAttackOperation] [remove] key ::: {}, value ::: {}, result ::: {}",
                key,
                value,
                result);
        return result;
    }

    @Override
    public Boolean delete(RedisOperations<String, Object> operations, TimeAttackVO vo) {
        String key = vo.getKey();
        Boolean result = operations.delete(key);
        log.debug("[TimeAttackOperation] [delete] key ::: {}, result ::: {}", key, result);
        return result;
    }

    @Override
    public Boolean expire(
            RedisOperations<String, Object> operations, TimeAttackVO vo, Duration duration) {
        String key = vo.getKey();
        Boolean result = operations.expire(key, duration);
        log.debug(
                "[TimeAttackOperation] [expire] key ::: {}, expire ::: {}, result ::: {}",
                key,
                duration,
                result);
        return result;
    }

    @Override
    public String generateValue(TimeAttackVO vo) {
        return String.valueOf(vo.getUserId());
    }

    @Override
    public void execute(RedisOperations<String, Object> operations, TimeAttackVO vo) {
        this.count(operations,vo);
        this.add(operations,vo);

    }
}