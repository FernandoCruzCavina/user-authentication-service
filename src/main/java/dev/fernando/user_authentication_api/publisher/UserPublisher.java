package dev.fernando.user_authentication_api.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dev.fernando.user_authentication_api.dto.AuthUserDto;
import dev.fernando.user_authentication_api.dto.SendEmailDto;
import dev.fernando.user_authentication_api.dto.UserEventDto;
import dev.fernando.user_authentication_api.enums.CreationType;
import dev.fernando.user_authentication_api.model.User;

/**
 * Publisher class for sending user-related events to RabbitMQ.
 * This class is responsible for publishing user creation events, email notifications,
 * and user credentials to the appropriate RabbitMQ exchanges and queues.
 * 
 * @author Fernando Cruz Cavina
 * @since 1.0.0
 */
@Component
public class UserPublisher {
    RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.exchance.userExchange}")
    private String exchangeUserEvent;
    @Value(value = "${broker.queue.email.sender}")
    private String routingKeyEmail;
    @Value(value = "${broker.queue.create.account}")
    private String routingKeyAccount;
    @Value(value = "${broker.queue.create.auth}")
    private String routingKeyAuth;
    
    public UserPublisher(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Publishes a message to the account creation in account-microservice.
     *
     * @param userEventDto the user event data to be published
     * @param creationType the type of creation event (e.g., CREATE, UPDATE)
     */
    public void publishUserEvent(UserEventDto userEventDto, CreationType creationType) {
        userEventDto.setCreationType(creationType.toString());
        rabbitTemplate.convertAndSend(exchangeUserEvent, "", userEventDto);
    }

    /**
     * Publishes an email message to the email queue for a newly created user.
     *
     * @param createdUser the user whose email will be sent
     */
    public void publishMessageEmail(User createdUser) {
        var emailDto = new SendEmailDto();

        emailDto.setEmailTo(createdUser.getEmail());
        emailDto.setSubject("Seu cadastro foi realizado com sucesso!");
        emailDto.setText("Bem-vindo, " + createdUser.getUsername()
                + "!\nObrigado por se registrar. Esperamos que você aproveite os serviços oferecidos pelo nosso banco.");

        rabbitTemplate.convertAndSend("", routingKeyEmail, emailDto);
    }

    /**
     * Publishes a message to the account creation in email-sender-microservice with the user ID.
     *
     * @param userId the ID of the user for whom the account will be created
     */
    public void publishAccountCreation(Long userId) {
        rabbitTemplate.convertAndSend("", routingKeyAccount, userId);
    }

    /**
     * Publishes user credentials to the authentication microservice with email and password.
     *
     * @param authUserDto the authentication data to be sent
     */
    public void publishUserCredentials(AuthUserDto authUserDto) {
        rabbitTemplate.convertAndSend("", routingKeyAuth, authUserDto);
    }
}
