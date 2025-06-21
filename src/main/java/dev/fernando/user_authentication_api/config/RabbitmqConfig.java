package dev.fernando.user_authentication_api.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Configuration class for RabbitMQ settings.
 * This class defines the queues, exchanges, and message converters used in the application.
 * It is responsible for setting up the messaging infrastructure for user-related events.
 * 
 * @author Fernando Cruz Cavina
 * @since 1.0.0
 */
@Configuration
public class RabbitmqConfig {
    /**
     * Queue name for email sending.
     * This queue is used to handle email sending requests in the application.
     * It is defined in the application properties file.
     */
    @Value("${broker.queue.email.sender}")
    public String emailQueue;

    /**
     * Queue name for account creation.
     * This queue is used to handle account creation requests in the application.
     * It is defined in the application properties file.
     */
    @Value("${broker.queue.create.account}")
    public String createAccount;

    /**
     * Queue name for user credentials creation.
     * This queue is used to handle user credentials creation requests in the application.
     * It is defined in the application properties file.
     */
    @Value("${broker.queue.create.auth}")
    public String createCredentials;

    /**
     * Exchange name for user events.
     * This exchange is used to broadcast user-related events to multiple queues.
     * It is defined in the application properties file.
     */
    @Value("${broker.exchance.userExchange}")
    public String exchangeUserEvent;

    /**
     * Bean definition for the email queue.
     * This queue is used to handle email sending requests.
     * It is created as a durable queue, meaning it will survive broker restarts.
     * 
     * @return a new instance of Queue for email sending
     */
    @Bean
    public Queue queue() {
        return new Queue(emailQueue, true);
    }

    /**
     * Bean definition for the account creation queue.
     * This queue is used to handle account creation requests.
     * It is created as a durable queue, meaning it will survive broker restarts.
     * 
     * @return a new instance of Queue for account creation
     */
    @Bean
    public Queue credentialsQueue(){
        return new Queue(createCredentials, true);
    }

    /**
     * Bean definition for the user credentials creation queue.
     * This queue is used to handle user credentials creation requests.
     * It is created as a durable queue, meaning it will survive broker restarts.
     * 
     * @return a new instance of Queue for user credentials creation
     */
    @Bean
    public Jackson2JsonMessageConverter objectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    /**
     * Bean definition for the fanout exchange for user events.
     * This exchange is used to broadcast user-related events to multiple queues.
     * It is created as a durable exchange, meaning it will survive broker restarts.
     * 
     * @return a new instance of FanoutExchange for user events
     */
    @Bean
    public FanoutExchange fanoutExchangeUser() {
        return new FanoutExchange(exchangeUserEvent);
    }

}
