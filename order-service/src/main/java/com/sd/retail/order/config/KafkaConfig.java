package com.sd.retail.order.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.sd.retail.commons.KafkaConfigProperties.ORDER_BOOKING_EVENTS_TOPIC;

@Configuration
public class KafkaConfig {
   @Bean
   public NewTopic createTransactionTopic() {
       return new NewTopic(ORDER_BOOKING_EVENTS_TOPIC, 3, (short) 1);
   }
}
