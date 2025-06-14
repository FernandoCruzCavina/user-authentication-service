package dev.fernando.user_authentication_api.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Value("${broker.queue.email.sender}")
    public String emailQueue;

    @Value("${broker.queue.create.account}")
    public String createAccount;

    @Value("${broker.queue.create.auth}")
    public String createCredentials;

    @Value("${broker.exchance.userExchange}")
    public String exchangeUserEvent;

    @Bean
    public Queue queue() {
        return new Queue(emailQueue, true);
    }

    @Bean
    public Queue credentialsQueue(){
        return new Queue(createCredentials, true);
    }

    @Bean
    public Jackson2JsonMessageConverter objectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public FanoutExchange fanoutExchangeUser() {
        return new FanoutExchange(exchangeUserEvent);
    }

}
