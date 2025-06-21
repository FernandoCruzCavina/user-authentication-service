package dev.fernando.user_authentication_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Security configuration class for the User Authentication API.
 * This class defines the password encoder bean used for hashing passwords.
 * 
 * @author Fernando Cruz Cavina
 * @since 1.0.0
 */
@Configuration
public class SecurityConfig {

    /**
     * Bean definition for BCryptPasswordEncoder.
     * This encoder is used to hash passwords securely.
     * 
     * @return a new instance of BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
