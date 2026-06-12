package com.sd.retail.order.config;

import com.sd.retail.commons.event.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

import static com.sd.retail.commons.KafkaConfigProperties.ORDER_BOOKING_EVENTS_TOPIC;

@Configuration
public class OrderPublisherConfig {
   @Bean
   public NewTopic createTransactionTopic() {
       return new NewTopic(ORDER_BOOKING_EVENTS_TOPIC, 3, (short) 1);
   }
}
