package com.esi.orderservice.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


/* Task 1   */
@Configuration
public class KafkaTopicConfiguration {

    @Bean
    public NewTopic OrderTopicCreation(){
    return TopicBuilder.name("orderCreatedTopic")
    .build();
    }

    @Bean
    public NewTopic PaymentTopicCreation(){
    return TopicBuilder.name("paymentTopic")
    .build();
    }
}
