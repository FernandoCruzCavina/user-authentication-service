package dev.fernando.user_authentication_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for sending email notifications.
 * Contains recipient email, subject, and message text.
 * 
 * 
 * @author Fernando Cruz Cavina
 * @since 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SendEmailDto {

    /**
     * The email address of the recipient.
     */
    String emailTo;
    /**
     * The subject of the email.
     */
    String subject;
    /**
     * The text content of the email.
     */
    String text;

}
