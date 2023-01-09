package com.shoppingmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableFeignClients
@EnableScheduling
@EnableJpaRepositories
@SpringBootApplication
public class ApiCouponApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCouponApplication.class, args);
	}

}
