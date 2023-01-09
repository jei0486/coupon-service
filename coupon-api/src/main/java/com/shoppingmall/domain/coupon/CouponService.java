package com.shoppingmall.domain.coupon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingmall.domain.TimeAttackOperation;
import com.shoppingmall.dto.NotificationDto;
import com.shoppingmall.dto.UserCouponResponseDto;
import com.shoppingmall.entity.UserCouponEntity;
import com.shoppingmall.enums.CouponStatus;
import com.shoppingmall.repository.UserCouponRepositoryImpl;
import com.shoppingmall.vo.TimeAttackVO;
import com.shoppingmall.dto.TimeAttackRequestDto;
import com.shoppingmall.kafka.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
@Service
public class CouponService {

    final TimeAttackOperation timeAttackOperation;
    final RedisOperations<String,Object> redisOperation;
    final KafkaService kafkaService;

    final UserCouponRepositoryImpl userCouponRepositoryImpl;

    final ObjectMapper mapper;
    final String successMsg = "success";
    final String failMsg = "fail";

    /**
     * 쿠폰 발행 (고객)
     *
     * @param dto
     * @return
     * @throws JsonProcessingException
     */
    public String couponIssued(TimeAttackRequestDto dto) throws JsonProcessingException {

        log.info(String.valueOf(dto));

        TimeAttackVO vo = TimeAttackVO.builder()
                .key(dto.getKey())
                .user_id(dto.getUser_id())
                .build();

        Long cnt = timeAttackOperation.count(redisOperation, vo);
        Long result = Long.valueOf(0);

        if (cnt != null && cnt < 100 ){
            result = timeAttackOperation.add(redisOperation, vo);
        }

        log.info("result : {}",result == 1 ?successMsg:failMsg);

        if (result == 1) {
            kafkaService.async("TimeAttackCouponIssue",mapper.writeValueAsString(dto));
            return successMsg;
        }else {
            return failMsg;
        }

    }

    public String couponIssuedNoneKafka(TimeAttackRequestDto dto)  {

        log.info(String.valueOf(dto));

        TimeAttackVO vo = TimeAttackVO.builder()
                .key(dto.getKey())
                .user_id(dto.getUser_id())
                .build();

        Long cnt = timeAttackOperation.count(redisOperation, vo);
        Long result = Long.valueOf(0);

        if (cnt != null && cnt < 100 ){
            result = timeAttackOperation.add(redisOperation, vo);
        }

        log.info("result : {}",result == 1 ?successMsg:failMsg);

        if (result == 1) {
            return successMsg;
        }else {
            return failMsg;
        }
    }

    /**
     * 쿠폰 list (고객)
     * List<UserCouponResponseDto>
     *
     * @return
     */
//    public List<UserCouponResponseDto> getCoupon(){
//        List<UserCouponEntity> userCouponEntityList = userCouponRepository.findAll();
//
//        List<UserCouponResponseDto> userCouponResponseDtoList = userCouponEntityList.stream()
//                .map(UserCouponEntity::toResponseDto)
//                .collect(Collectors.toList());
//
//        return userCouponResponseDtoList;
//    }

    /*
     * 만료 하루전
     * 특정_status와_특정_날짜_구간의_만료일에_해당하는_쿠폰_목록_조회
     * */
    public List<UserCouponResponseDto> findAllByEndDateBetweenToday(){

        long afterDays = 1L;
        LocalDateTime afterNDays = LocalDateTime.now().plusDays(afterDays);
        LocalDateTime startDate = LocalDateTime.of(afterNDays.getYear(), afterNDays.getMonth(), afterNDays.getDayOfMonth(), 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(afterNDays.getYear(), afterNDays.getMonth(), afterNDays.getDayOfMonth(), 23, 59, 59);

        log.info("startDate : {} , endDate : {}",startDate,endDate);




        List<UserCouponEntity> userCouponEntityList = userCouponRepositoryImpl.findAllByStatusAndEndDtBetween(CouponStatus.PUBLISHED,startDate,endDate);

        log.info("userCouponEntityList : {}",String.valueOf(userCouponEntityList));

        // stream vs parallelStream
        return userCouponEntityList.stream()
                .map(UserCouponEntity::toResponseDto)
                .collect(Collectors.toList());
    }

    public void publishNotification(List<UserCouponResponseDto> userCouponResponseDtoList){

        String topicName = "NotificationExpireCoupon";

        userCouponResponseDtoList.parallelStream()
                .map(e ->
                        NotificationDto.builder()
                                .user_id(e.getUser_id())
                                .code(e.getC_name())
                                .notificationMsg("쿠폰이 24시간후 만료됩니다.")
                                .expiration(e.getEnd_dt())
                                .build())
               // .forEach(n -> producer.publish(topicName, n.getExpirationDate().getDayOfWeek().getValue(), n));
                .forEach(n -> {
                    try {
                        kafkaService.async(topicName,mapper.writeValueAsString(n));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

}
