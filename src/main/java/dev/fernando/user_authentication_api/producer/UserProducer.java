package dev.fernando.user_authentication_api.producer;

import dev.fernando.user_authentication_api.dto.SendEmailDto;
import dev.fernando.user_authentication_api.model.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.email.sender}")
    private String routingKey;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishMessageEmail(User createdUser) {
        SendEmailDto emailDto = new SendEmailDto();

        emailDto.setEmailTo(createdUser.getEmail());
        emailDto.setName(createdUser.getUsername());
        emailDto.setSubject("Your registration has been successfully completed");
        emailDto.setText("Welcome, " + createdUser.getUsername() + "!\nThank you for registering. We hope you enjoy the services offered by our bank.");

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }
}
