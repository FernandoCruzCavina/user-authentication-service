package dev.fernando.user_authentication_api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.fernando.user_authentication_api.dto.CreateUserDto;
import dev.fernando.user_authentication_api.dto.UpdateUserDto;
import dev.fernando.user_authentication_api.dto.ViewUserDto;
import dev.fernando.user_authentication_api.service.UserService;

/**
 * REST controller for managing users.
 * Provides endpoints for creating, retrieving, updating, and deleting users.
 * Uses HATEOAS for response enrichment.
 * 
 * @author Fernando Cruz Cavina
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final static String idString = "id";
    private final static String userString = "user";
    private final static String update = "update";
    private final static String delete = "delete";

    /**
     * Constructs a UserController with the given UserService.
     * @param userService the user service that provides user-related operations
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * This endpoint allows retrieval of user details using their ID.
     * @param id the user ID
     * @return the user details wrapped in an EntityModel
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<EntityModel<ViewUserDto>> getUserById(@PathVariable("id") Long id) {
        ViewUserDto user = userService.findUserById(id);

        EntityModel<ViewUserDto> model = EntityModel.of(user);
        model.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
        model.add(linkTo(methodOn(UserController.class).updateUser(id, null)).withRel(update));
        model.add(linkTo(methodOn(UserController.class).deleteUserById(id)).withRel(delete));

        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    /**
     * This endpoint allows retrieval of user details using their email address.
     * @param email the user email
     * @return the user details wrapped in an EntityModel
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<EntityModel<ViewUserDto>> getUserByEmail(@PathVariable("email") String email) {
        ViewUserDto user = userService.findUserByEmail(email);

        EntityModel<ViewUserDto> model = EntityModel.of(user);
        model.add(linkTo(methodOn(UserController.class).getUserByEmail(email)).withSelfRel());
        model.add(linkTo(methodOn(UserController.class)).slash(update).withRel(update));
        model.add(linkTo(methodOn(UserController.class).deleteUserByEmail(email)).withRel(delete));

        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    /**
     * This endpoint allows the creation of a user with a default role, typically used for client accounts.
     * @param createUserDto the user data sent in the request body
     * @return the created user details(ViewUserDto) wrapped in an EntityModel with HATEOAS links
     */
    @PostMapping
    public ResponseEntity<EntityModel<ViewUserDto>> createUser(@RequestBody CreateUserDto createUserDto) {
        ViewUserDto userDto = userService.createUserWithDefaultRole(createUserDto);

        EntityModel<ViewUserDto> model = EntityModel.of(userDto);
        model.add(linkTo(methodOn(UserController.class).createUser(createUserDto)).withSelfRel());
        model.add(linkTo(methodOn(UserController.class)).slash(idString).withRel(userString));
        model.add(linkTo(methodOn(UserController.class)).slash(idString).withRel(update));
        model.add(linkTo(methodOn(UserController.class)).slash(idString).withRel(delete));

        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    /**
     * This endpoint allows updating user details by ID.
     * The user must provide the current password to confirm the update.
     * @param id the user ID wanting to be updated
     * @param updateUserDto the updated user data sent in the request body
     * @return the updated user details(ViewUserDto) wrapped in an EntityModel with HATEOAS links
     */
    @PutMapping("/id/{id}")
    public ResponseEntity<EntityModel<ViewUserDto>> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto updateUserDto) {
        ViewUserDto userDto = userService.updateUser(id, updateUserDto);

        EntityModel<ViewUserDto> model = EntityModel.of(userDto);
        model.add(linkTo(methodOn(UserController.class).updateUser(id, updateUserDto)).withSelfRel());
        model.add(linkTo(methodOn(UserController.class).getUserById(id)).withRel("user"));
        model.add(linkTo(methodOn(UserController.class).deleteUserById(id)).withRel("delete"));

        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    /**
     * This endpoint deletes a user by ID.
     * @param id the user ID to be deleted
     * @return the deleted user details wrapped in an EntityModel
     */
    @DeleteMapping("/id/{id}")
    public ResponseEntity<EntityModel<ViewUserDto>> deleteUserById(@PathVariable("id") Long id) {
        ViewUserDto userDto = userService.deleteUserById(id);

        EntityModel<ViewUserDto> model = EntityModel.of(userDto);
        model.add(linkTo(methodOn(UserController.class).deleteUserById(id)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    /**
     * This endpoint deletes a user by email.
     * @param email the user email to be deleted
     * @return the deleted user details wrapped in an EntityModel
     */
    @DeleteMapping("/email/{email}")
    public ResponseEntity<EntityModel<ViewUserDto>> deleteUserByEmail(@PathVariable String email) {
        ViewUserDto userDto = userService.deleteUserByEmail(email);

        EntityModel<ViewUserDto> model = EntityModel.of(userDto);
        model.add(linkTo(methodOn(UserController.class).deleteUserByEmail(email)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(model);
    }
}
