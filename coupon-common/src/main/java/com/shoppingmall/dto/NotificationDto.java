package com.shoppingmall.dto;

import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Builder
public class NotificationDto {

    String code;
    String notificationMsg;
    LocalDateTime expiration;
    String user_id;
}
