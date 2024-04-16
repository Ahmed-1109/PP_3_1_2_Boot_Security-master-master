package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.exception_handling.NoSuchUserException;
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
        return em.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    public User getUserById(Long id) {
        return Optional.ofNullable(em.createQuery("select u from User u JOIN FETCH u.roles where u.id =:userId", User.class)
                        .setParameter("userId", id)
                        .getSingleResult())
                .orElseThrow(NoSuchUserException::new);
    }

    @Override
    public void removeUser(Long id) {
        em.remove(em.find(User.class, id));
    }

    @Override
    public void updateUser(User user) {
        final User existUser = getUserById(user.getId());
        em.merge(user);
    }

    @Override
    public User findByUserName(String email) {

        return Optional.ofNullable(em.createQuery("SELECT u from User u  join fetch u.roles WHERE u.email = :email", User.class).
                        setParameter("email", email)
                        .getSingleResult())
                .orElseThrow(NoSuchUserException::new);
    }
}

