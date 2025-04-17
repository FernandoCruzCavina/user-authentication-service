package dev.fernando.user_authentication_api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.fernando.user_authentication_api.dto.JwtUserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.security.key}")
    private String secretKey;
    @Value("${jwt.security.inssuer}")
    private String issuer;
    @Value("${jwt.security.expiration}")
    private int expiration= 60*1000*60;

    public JwtUtils() {
    }

    public JwtUtils(String secretKey, String issuer, int expiration) {
        this.secretKey = secretKey;
        this.issuer = issuer;
        this.expiration = expiration;
    }

    public String generateToken(JwtUserDto jwtUserDto) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            String userJson = new ObjectMapper().writeValueAsString(jwtUserDto);

            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(userJson)
                    .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                    .sign(algorithm);

        } catch (JWTCreationException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException(e);
        }
    }
}
