package dev.fernando.user_authentication_api.dto;

import lombok.Data;

@Data
public class UserEventDto {
    private Long idUser;
    private String creationType;
}
