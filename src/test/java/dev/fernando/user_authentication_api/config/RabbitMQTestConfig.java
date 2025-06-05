package dev.fernando.user_authentication_api.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class RabbitMQTestConfig {

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return mock(RabbitTemplate.class); 
    }

}

