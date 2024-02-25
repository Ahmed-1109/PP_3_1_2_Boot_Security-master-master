package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getUserById(int id);
    void save(User user);

    User getUserByUsername(String username);

    List<User> getAllUsers();

    void update(int id, User user);

    void removeUser(int id);

}
