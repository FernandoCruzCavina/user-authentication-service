package dev.fernando.user_authentication_api.model;

import dev.fernando.user_authentication_api.dto.UserEventDto;
import dev.fernando.user_authentication_api.dto.ViewUserDto;
import dev.fernando.user_authentication_api.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Represents a user in the system.
 * Implements UserDetails for Spring Security integration.
 * Contains user information such as username, email, password, phone, cpf, birthday date, and user role.
 * Provides methods to convert to DTOs and manage user roles.
 * 
 * @author Fernando Cruz Cavina
 * @since 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String cpf;
    private Long birthdayDate;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public User(String username, String email, String password, String phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public User(String username, String email, String password, String phone, UserRole userRole) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userRole = userRole;
    }

    public User(String username, String email, String password, String phone, String cpf, Long birthdayDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.cpf = cpf;
        this.birthdayDate = birthdayDate;
    }

    public User(Long id, String username, String email, String password, String phone, String cpf, Long birthdayDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.cpf = cpf;
        this.birthdayDate = birthdayDate;
    }

    public User(String username, String email, String password, String phone, String cpf, Long birthdayDate,
            UserRole userRole) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.cpf = cpf;
        this.birthdayDate = birthdayDate;
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.userRole == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }
    
    /**
     * Converts the User entity to a UserEventDto.
     * This method is used to prepare user data for event publishing, such as account creation or updates.
     * @return
     */
    public UserEventDto convertToUserEventDto() {
        var userEventDto = new UserEventDto();

        BeanUtils.copyProperties(this, userEventDto);
        userEventDto.setIdUser(this.getId());

        return userEventDto;
    }

    /**
     * Converts the User entity to a ViewUserDto.
     * This method is used to prepare user data for API responses.
     * 
     * @return a ViewUserDto object containing user details
     */
    public  ViewUserDto toViewUserDto(){
        var userDto = new ViewUserDto(
            this.id,
            this.username,
            this.email,
            this.phone,
            new Date(this.birthdayDate),
            this.cpf,
            this.userRole
        );

        return userDto;
    }
}
