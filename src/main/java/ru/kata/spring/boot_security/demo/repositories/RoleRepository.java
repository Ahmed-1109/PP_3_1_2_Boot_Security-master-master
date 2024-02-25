package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleRepository {
    void save(Role userRole);
    Role getRoleByName(String name);

    List<Role> getAllRoles();

    Role getRoleById(int roleId);
}
