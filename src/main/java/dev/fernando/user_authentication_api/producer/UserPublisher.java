package dev.fernando.user_authentication_api.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dev.fernando.user_authentication_api.dto.UserEventDto;
import dev.fernando.user_authentication_api.enums.CreationType;

@Component
public class UserPublisher {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.exchance.userExchange}")
    private String exchangeUserEvent;

    public void publishUserEvent(UserEventDto UserEventDto, CreationType creationType) {
        UserEventDto.setCreationType(creationType.toString());
        rabbitTemplate.convertAndSend(exchangeUserEvent, "", UserEventDto);
    }

}
