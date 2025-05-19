package dev.fernando.user_authentication_api.entity;

import dev.fernando.user_authentication_api.constants.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String cpf;
    private long birthday_date;
    @Enumerated(EnumType.STRING)
    private UserRole user_role;


    public User(String username, String email, String password, String phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public User(String username, String email, String password, String phone, UserRole user_role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.user_role = user_role;
    }

    public User(String username, String email, String password, String phone, String cpf, long birthday_date, UserRole user_role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.cpf = cpf;
        this.birthday_date = birthday_date;
        this.user_role = user_role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.user_role ==UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

}
