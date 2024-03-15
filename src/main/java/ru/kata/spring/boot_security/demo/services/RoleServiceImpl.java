package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public void addRole(Role role) {
        roleRepository.addRole(role);
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        return roleRepository.getRoleByName(name);
    }

    @Override
    public Set<Role> getRoleByNames(Set<String> roleNames) {
        return roleRepository.getRoleByNames(roleNames);
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.getRoleById(id);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.getRoles();
    }
}
