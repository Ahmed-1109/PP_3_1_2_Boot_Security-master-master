package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public RoleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        return entityManager.createQuery("select u from Role u where u.name =:name", Role.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst();
    }
    @Override
    public Role findRoleByName(String name) {
        return entityManager.createQuery("SELECT role FROM Role role WHERE role.name=:name", Role.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    /*@Override
    public Set<Role> getRoleByNames(Set<String> roleNames) {
        return new HashSet<>(entityManager.createQuery("select u from Role u where u.name in (:roleNames)", Role.class)
                .setParameter("roleNames", roleNames)
                .getResultList());
    }*/

    @Override
    public Optional<Role> getRoleById(Long id) {
        return entityManager.createQuery("select u from Role u where u.id =:id", Role.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<Role> getRoles() {
        return entityManager.createQuery("from Role", Role.class).getResultList();
    }
}
