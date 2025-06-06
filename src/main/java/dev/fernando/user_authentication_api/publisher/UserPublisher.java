package dev.fernando.user_authentication_api.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dev.fernando.user_authentication_api.dto.AuthUserDto;
import dev.fernando.user_authentication_api.dto.SendEmailDto;
import dev.fernando.user_authentication_api.dto.UserEventDto;
import dev.fernando.user_authentication_api.enums.CreationType;
import dev.fernando.user_authentication_api.model.User;

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

    public void publishUserEvent(UserEventDto userEventDto, CreationType creationType) {
        userEventDto.setCreationType(creationType.toString());
        rabbitTemplate.convertAndSend(exchangeUserEvent, "", userEventDto);
        System.out.println("sender to account service");
    }

    public void publishMessageEmail(User createdUser) {
        var emailDto = new SendEmailDto();

        emailDto.setEmailTo(createdUser.getEmail());
        emailDto.setSubject("Your registration has been successfully completed");
        emailDto.setText("Welcome, " + createdUser.getUsername()
                + "!\nThank you for registering. We hope you enjoy the services offered by our bank.");

        rabbitTemplate.convertAndSend("", routingKeyEmail, emailDto);
    }

    public void publishAccountCreation(Long userId) {
        rabbitTemplate.convertAndSend("", routingKeyAccount, userId);
    }

    public void publishUserCredentials(AuthUserDto authUserDto) {
        rabbitTemplate.convertAndSend("", routingKeyAuth, authUserDto);
        System.out.println(authUserDto.userRole());
    }
}
