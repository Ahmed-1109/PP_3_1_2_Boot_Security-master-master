package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class RoleRepositoryImpl implements RoleRepository{

    @PersistenceContext
    private EntityManager em;

    public RoleRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Role role) {
        em.persist(role);
    }

    @Override
    public Role getRoleByName(String name) {
        return (Role) em.createQuery("select rl from Role rl where rl.name = ?1").setParameter(1, name).getSingleResult();
    }

    @Override
    public List<Role> getAllRoles() {
        return em.createQuery("select rl from Role rl", Role.class).getResultList();
    }

    @Override
    public Role getRoleById(int roleId) {
        return em.find(Role.class, roleId);
    }

}
