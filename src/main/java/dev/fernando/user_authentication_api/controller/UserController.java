package dev.fernando.user_authentication_api.controller;

import dev.fernando.user_authentication_api.dto.CreateUserDto;
import dev.fernando.user_authentication_api.dto.UpdateUserDto;
import dev.fernando.user_authentication_api.dto.ViewUserDto;
import dev.fernando.user_authentication_api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/id={id}")
    public ResponseEntity<ViewUserDto> getUserById(@PathVariable("id") int id) {
        ViewUserDto user = userService.findUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    @GetMapping("/get/email={email}")
    public ResponseEntity<ViewUserDto> getUserByEmail(@PathVariable("email") String email) {
        ViewUserDto user = userService.findUserByEmail(email);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/create")
    public ResponseEntity<ViewUserDto> createUser(@RequestBody CreateUserDto user) {
        ViewUserDto userDto = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ViewUserDto> updateUser(@RequestBody UpdateUserDto user) {
        ViewUserDto userDto = userService.updateUser(user);

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @DeleteMapping("/delete/id={id}")
    public ResponseEntity<ViewUserDto> deleteUserById(@PathVariable("id") int id) {
        ViewUserDto userDto = userService.deleteUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @DeleteMapping("/delete/emai={email}")
    public ResponseEntity<ViewUserDto> deleteUserByEmail(@PathVariable String email) {
        ViewUserDto userDto = userService.deleteUserByEmail(email);

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

}
