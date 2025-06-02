package dev.fernando.user_authentication_api.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public record CreateUserDto(
    String username,
    String email,
    String password,
    String phone,
    String cpf,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date birthday_date
){}
