package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    public UserRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<User> getUsers() {
        return em.createQuery("from users", User.class).getResultList();
    }

    @Override
    public void addUser(User user) {
        em.persist(user);
        //return true;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return em.createQuery("select u from users u JOIN FETCH u.roles where u.id =:userId", User.class)
                .setParameter("userId", id)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public void removeUser(Long id) {
        User user = em.find(User.class, id);
        em.remove(user);
    }

    @Override
    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    public Optional<User> findByUserName(String email) {
        return em.createQuery("select u from users u JOIN FETCH u.roles where u.email =:email", User.class)
                .setParameter("email", email)
                .getResultList()
                .stream()
                .findFirst();
    }
}

