package dev.fernando.user_authentication_api.dto;

import lombok.Data;

/**
 * Data Transfer Object for user event messages.
 * Used for publishing user-related events to other services.
 * 
 * 
 * @author Fernando Cruz Cavina
 * @since 1.0.0
 */
@Data
public class UserEventDto {
    /** 
     * the ID of the user associated with the event 
    */
    private Long idUser;
    /**
     * the type of event (e.g., "created", "updated") 
     */
    private String creationType;
}
