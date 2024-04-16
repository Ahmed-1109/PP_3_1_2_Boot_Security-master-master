package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void addUser(User user);

    List<User> getUsers();

    User getUserById(Long id);

    void removeUser(Long id);

    void updateUser(User user);

    User findByUserName(String email);
}
