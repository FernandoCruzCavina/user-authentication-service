package dev.fernando.user_authentication_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SendEmailDto {

    String emailTo;
    String subject;
    String text;

}
