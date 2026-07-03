package com.sd;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.server.mvc.config.GatewayMvcProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
       SpringApplication.run(ApiGatewayApplication.class, args);
    }
    @Bean
    ApplicationRunner runner(ApplicationContext context) {
        return args -> {

            System.out.println(
                    GatewayMvcProperties.class.getAnnotation(
                            org.springframework.boot.context.properties.ConfigurationProperties.class
                    )
            );

            Arrays.stream(context.getBeanDefinitionNames())
                    .filter(name -> name.toLowerCase().contains("gateway"))
                    .sorted()
                    .forEach(System.out::println);
        };
    }
}