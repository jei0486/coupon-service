
package com.shoppingmall.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.LoggingErrorHandler;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class KafkaConsumerConfig {

    private final KafkaProperties kafkaProperties;


    // 실시간 쿠폰 발행
    @Bean
    public ConsumerFactory<String, String> timeAttackCouponConsumerFactory() {
        return setConsumerFactory(kafkaProperties.getConsumer().getCommonConfig());
    }

    // 쿠폰 만료 알림
    @Bean
    public ConsumerFactory<String, String> notificationExpireCouponConsumerFactory() {
        // notificationExpireCouponConsumerConfig
        return setConsumerFactory(kafkaProperties.getConsumer().getCommonConfig());
    }

    // 추가 가능 ....

    private ConsumerFactory<String, String> setConsumerFactory(final KafkaProperties.ConsumerCommonConfig consumerCommonConfig) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerCommonConfig.getBootstrapServers());
        configProps.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, consumerCommonConfig.getTimeoutMs());
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, consumerCommonConfig.getGroupId());
        configProps.put(ConsumerConfig.CLIENT_ID_CONFIG, consumerCommonConfig.getClientId());
        configProps.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, consumerCommonConfig.getFetchMinBytes());
        configProps.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, consumerCommonConfig.getFetchMaxWaitMs());

        return new DefaultKafkaConsumerFactory<>(configProps);
    }


    // 추가 가능 ....

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,String> timeAttackCouponKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(timeAttackCouponConsumerFactory());
        factory.setErrorHandler(new LoggingErrorHandler());
        return factory;
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,String> notificationExpireCouponKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(notificationExpireCouponConsumerFactory());
        factory.setErrorHandler(new LoggingErrorHandler());
        return factory;
    }
}