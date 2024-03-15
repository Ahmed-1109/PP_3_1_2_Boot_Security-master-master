package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleService {
    void addRole(Role role);

    Optional<Role> getRoleByName(String name);

    Set<Role> getRoleByNames(Set<String> roleNames);

    List<Role> getRoles();

    Optional<Role> getRoleById(Long id);
}
