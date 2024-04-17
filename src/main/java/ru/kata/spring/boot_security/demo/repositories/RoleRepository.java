package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    void addRole(Role role);

    Optional<Role> getRoleByName(String name);

    List<Role> getRoles();


    Optional<Role> getRoleById(Long id);

    Role findRoleByName(String name);

}
