package com.sd.retail.inventory.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import static com.sd.retail.commons.KafkaConfigProperties.INVENTORY_RESERVED_TOPIC;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic createTransactionTopic() {
        return new  NewTopic(INVENTORY_RESERVED_TOPIC,3,(short)1);
    }
}
