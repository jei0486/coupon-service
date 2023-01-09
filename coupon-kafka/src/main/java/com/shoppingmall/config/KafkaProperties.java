package com.shoppingmall.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProperties {

    private String defaultBootstrapServers;
    private Consumer consumer = new Consumer();

    @Data
    public class Consumer {

        private ConsumerCommonConfig commonConfig = new ConsumerCommonConfig();
        private ConsumerConfig timeAttackCouponConsumerConfig = new ConsumerConfig();

        // 추가 가능
    }

    @Data
    public class ConsumerCommonConfig {

        private String groupId;
        private String clientId;
        private List<String> bootstrapServers;
        private String timeoutMs;
        private int fetchMinBytes;
        private int fetchMaxWaitMs;
    }

    @Data
    public class ConsumerConfig {

        private String topic;
    }
}

