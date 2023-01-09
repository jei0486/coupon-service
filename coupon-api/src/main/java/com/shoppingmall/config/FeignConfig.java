package com.shoppingmall.config;


import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {



    /*
     * feign 로깅 처리
     * */
    @Bean
    Logger.Level feignLoggerLevel(){return Logger.Level.FULL;}

    /*
     * feign decoder 처리
     * */
    @Bean
    Decoder feginDecoder(){
        return new FeignDecoder();
    }

    /*
     * header setting
     *
     * interceptor 역할
     * */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("PRIVATE-TOKEN", "JnaLxyMLoXe19Lzvoxdx");
        };
    }

}

