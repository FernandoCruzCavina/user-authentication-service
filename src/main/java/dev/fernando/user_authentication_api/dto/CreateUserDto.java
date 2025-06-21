package dev.fernando.user_authentication_api.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import dev.fernando.user_authentication_api.model.User;

/**
 * Data Transfer Object for creating a new user.
 * Contains user registration information such as username, email, password, phone, cpf, and birthday date.
 * 
 * @param username the user's name
 * @param email the user's email
 * @param password the user's password
 * @param phone the user's phone number
 * @param cpf the user's CPF (Brazilian ID)
 * @param birthdayDate the user's birthday date
 */
public record CreateUserDto(
    String username,
    String email,
    String password,
    String phone,
    String cpf,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date birthdayDate
){
    /**
     * Converts this CreateUserDto to a User model object.
     * 
     * @return a User object populated with the data from this DTO
     */
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
    /**
     * Converts a Date object to its epoch time representation in milliseconds for storage in the database.
     * 
     * @param date the Date object to convert
     * @return the epoch time in milliseconds, or null if the date is null
     */
    public Long dateToEpoch(Date date) {
        return date == null ? null : date.getTime();
    }
}
