package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    void addUser(User user);

    BindingResult checkUsername(BindingResult bindingResult, User user);

    List<User> getUsers();

    Optional<User> getUserById(Long id);

    void removeUser(Long id);

    void updateUser(User user, BindingResult bindingResult);

    Optional<User> findByUserName(String email);
}
