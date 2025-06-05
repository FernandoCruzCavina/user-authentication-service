package dev.fernando.user_authentication_api.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import dev.fernando.user_authentication_api.model.User;

public record CreateUserDto(
    String username,
    String email,
    String password,
    String phone,
    String cpf,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date birthdayDate
){
    public User toUser(){
        var user = new User();

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setCpf(cpf);
        user.setBirthdayDate(dateToEpoch(birthdayDate));

        return user;
    }

    public Long dateToEpoch(Date date) {
        return date == null ? null : date.getTime();
    }
}
