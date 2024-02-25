package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleService {
    void save(Role role);

    Role getRoleByName(String name);

    List<Role> getAllRoles();

    Role getRoleById(int roleId);
}
