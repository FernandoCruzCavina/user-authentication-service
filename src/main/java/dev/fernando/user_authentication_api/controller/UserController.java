package dev.fernando.user_authentication_api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.fernando.user_authentication_api.dto.CreateUserDto;
import dev.fernando.user_authentication_api.dto.UpdateUserDto;
import dev.fernando.user_authentication_api.dto.ViewUserDto;
import dev.fernando.user_authentication_api.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final String idString = "id";
    private final String userString = "user";
    private final String update = "update";
    private final String delete = "delete";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<EntityModel<ViewUserDto>> getUserById(@PathVariable("id") Long id) {
        ViewUserDto user = userService.findUserById(id);

        EntityModel<ViewUserDto> model = EntityModel.of(user);
        model.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
        model.add(linkTo(methodOn(UserController.class).updateUser(id, null)).withRel(update));
        model.add(linkTo(methodOn(UserController.class).deleteUserById(id)).withRel(delete));

        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<EntityModel<ViewUserDto>> getUserByEmail(@PathVariable("email") String email) {
        ViewUserDto user = userService.findUserByEmail(email);

        EntityModel<ViewUserDto> model = EntityModel.of(user);
        model.add(linkTo(methodOn(UserController.class).getUserByEmail(email)).withSelfRel());
        model.add(linkTo(methodOn(UserController.class)).slash(update).withRel(update));
        model.add(linkTo(methodOn(UserController.class).deleteUserByEmail(email)).withRel(delete));

        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @PostMapping
    public ResponseEntity<EntityModel<ViewUserDto>> createUser(@RequestBody CreateUserDto user) {
        ViewUserDto userDto = userService.createUserWithDefaultRole(user);

        EntityModel<ViewUserDto> model = EntityModel.of(userDto);
        model.add(linkTo(methodOn(UserController.class).createUser(user)).withSelfRel());
        model.add(linkTo(methodOn(UserController.class)).slash(idString).withRel(userString));
        model.add(linkTo(methodOn(UserController.class)).slash(idString).withRel(update));
        model.add(linkTo(methodOn(UserController.class)).slash(idString).withRel(delete));

        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<EntityModel<ViewUserDto>> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto user) {
        ViewUserDto userDto = userService.updateUser(id, user);

        EntityModel<ViewUserDto> model = EntityModel.of(userDto);
        model.add(linkTo(methodOn(UserController.class).updateUser(id, user)).withSelfRel());
        model.add(linkTo(methodOn(UserController.class).getUserById(id)).withRel("user"));
        model.add(linkTo(methodOn(UserController.class).deleteUserById(id)).withRel("delete"));

        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<EntityModel<ViewUserDto>> deleteUserById(@PathVariable("id") Long id) {
        ViewUserDto userDto = userService.deleteUserById(id);

        EntityModel<ViewUserDto> model = EntityModel.of(userDto);
        model.add(linkTo(methodOn(UserController.class).deleteUserById(id)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @DeleteMapping("/email/{email}")
    public ResponseEntity<EntityModel<ViewUserDto>> deleteUserByEmail(@PathVariable String email) {
        ViewUserDto userDto = userService.deleteUserByEmail(email);

        EntityModel<ViewUserDto> model = EntityModel.of(userDto);
        model.add(linkTo(methodOn(UserController.class).deleteUserByEmail(email)).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

}
