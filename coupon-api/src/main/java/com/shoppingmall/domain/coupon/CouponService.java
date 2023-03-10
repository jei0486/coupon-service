package com.shoppingmall.domain.coupon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingmall.domain.TimeAttackOperation;
import com.shoppingmall.dto.NotificationDto;
import com.shoppingmall.dto.UserCouponResponseDto;
import com.shoppingmall.entity.UserCoupon;
import com.shoppingmall.enums.CouponStatus;
import com.shoppingmall.repository.UserCouponRepository;
import com.shoppingmall.util.ModelMapperUtil;
import com.shoppingmall.vo.TimeAttackVO;
import com.shoppingmall.dto.TimeAttackRequestDto;
import com.shoppingmall.kafka.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
@Service
public class CouponService {

    private final TimeAttackOperation timeAttackOperation;
    private final RedisOperations<String,Object> redisOperation;
    private final KafkaService kafkaService;
    private final UserCouponRepository userCouponRepository;

    private final ObjectMapper mapper;
    public static final String successMsg = "success";
    public static final String failMsg = "fail";

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
                .userId(dto.getUserId())
                .build();

        Long cnt = timeAttackOperation.count(redisOperation, vo);
        Long result = Long.valueOf(0);

        if (cnt != null && cnt < 100 ){
            result = timeAttackOperation.add(redisOperation, vo);
        }

        log.info("result : {}",result == 1 ? successMsg : "fail :: key is Duplicate in Redis");

        if (result == 1) {
            kafkaService.async("TimeAttackCouponIssue",mapper.writeValueAsString(dto));
            return successMsg;
        }else {
            return "fail :: key is Duplicate in Redis";
        }

    }

    public String couponIssuedNoneKafka(TimeAttackRequestDto dto)  {

        log.info(String.valueOf(dto));

        TimeAttackVO vo = TimeAttackVO.builder()
                .key(dto.getKey())
                .userId(dto.getUserId())
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

        log.info("==================================================");
        log.info("startDate : {} , endDate : {}",startDate,endDate);
        log.info("==================================================");


        List<UserCoupon> userCouponList = userCouponRepository.findAllByStatusAndEndDtBetween(CouponStatus.PUBLISHED,startDate,endDate);

        log.info("==================================================");
        log.info("userCouponEntityList : {}",String.valueOf(userCouponList));
        log.info("==================================================");

        // stream vs parallelStream
//        return userCouponEntityList.stream()
//                .map(UserCouponEntity::toResponseDto)
//                .collect(Collectors.toList());

        List<UserCouponResponseDto> collect = new ArrayList<>();

        if (!userCouponList.isEmpty()){
            collect = userCouponList.stream().map(entity -> ModelMapperUtil.map(entity,UserCouponResponseDto.class)).collect(Collectors.toList());
        }

        return collect;
    }

    public void publishNotification(List<UserCouponResponseDto> userCouponResponseDtoList){

        String topicName = "NotificationExpireCoupon";

        userCouponResponseDtoList.parallelStream()
                .map(e ->
                        NotificationDto.builder()
                                .userId(e.getUserId())
                                .code(e.getCouponName())
                                .notificationMsg("쿠폰이 24시간후 만료됩니다.")
                                .expiration(e.getEndDt())
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
