package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserRepository {
    List<User> getUsers();

    User getUserById(Long id);

    void removeUser(Long id);

    void updateUser(User user);

    User findByUserName(String userName);

    void addUser(User user);
}
