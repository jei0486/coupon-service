package com.shoppingmall.api;


import com.shoppingmall.util.PasswordEncryptUtil;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test2 {

    @Test
    void test(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        long afterDays = 1L;
        LocalDateTime afterNDays = LocalDateTime.now().plusDays(afterDays);
        LocalDateTime startDate = LocalDateTime.of(afterNDays.getYear(), afterNDays.getMonth(), afterNDays.getDayOfMonth(), 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(afterNDays.getYear(), afterNDays.getMonth(), afterNDays.getDayOfMonth(), 23, 59, 59);

        System.out.println("startDate :  "+  startDate + "  , endDate : " + endDate );


        System.out.println(Timestamp.valueOf(startDate));
        String a = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(a);
    }

    @Test
    void passwordEncTest(){
        PasswordEncryptUtil util = new PasswordEncryptUtil();
        System.out.println(util.encrypt("test"));
    }
}
