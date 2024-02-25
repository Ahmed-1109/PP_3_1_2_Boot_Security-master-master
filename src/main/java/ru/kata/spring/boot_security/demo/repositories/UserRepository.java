package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserRepository {
    User getUserById(int id);

    void save(User user);

    User getUserByUsername(String username);

    List<User> getAllUsers();

    void remove(int id);

    void update(int id, User user);
}
