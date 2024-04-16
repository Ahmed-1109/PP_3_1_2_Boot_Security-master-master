package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.exception_handling.NoSuchUserException;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestAPIController {
    private final UserService userService;

    public RestAPIController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/admin/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/admin")
    public User newUser(@RequestBody @Valid User user) {
        userService.addUser(user);
        return user;
    }

    @PutMapping("/admin/{id}")
    public User updateUser(@RequestBody @Valid User user,
                           @PathVariable Long id) {
        userService.updateUser(user);
        return user;
    }


    @DeleteMapping("/admin/{id}")
    public void deleteUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new NoSuchUserException();
        }
        userService.removeUser(id);
    }

}
