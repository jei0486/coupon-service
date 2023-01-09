package com.shoppingmall.api;

import com.shoppingmall.util.JusoUtil;
import org.junit.jupiter.api.Test;

public class JusoUtilTest {
    
    @Test
    void jusoTest(){
        // 마포구 도화-2길 코끼리분식
        // 강남대로84길
        JusoUtil jusoUtil = new JusoUtil();
        String result = jusoUtil.jusoUtil("마포구 도화-2길 코끼리분식");
        System.out.println(result);
    }

    @Test
    void jusoTest2(){
        // 마포구 도화-2길 코끼리분식
        // 강남대로84길
        JusoUtil jusoUtil = new JusoUtil();
        String result = jusoUtil.jusoUtil2("마포구 도화-2길 코끼리분식");
        System.out.println("-------------------------");
        System.out.println(result);
        System.out.println("-------------------------");
    }
}
