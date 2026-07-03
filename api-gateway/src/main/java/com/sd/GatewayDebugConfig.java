package com.sd;

import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.gateway.server.mvc.config.GatewayMvcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayDebugConfig {

    @Bean
    ApplicationRunner routes(GatewayMvcProperties properties) {
        return args -> {
            System.out.println("Routes found: " + properties.getRoutes().size());
            System.out.println(properties.getRoutes());
        };
    }
}