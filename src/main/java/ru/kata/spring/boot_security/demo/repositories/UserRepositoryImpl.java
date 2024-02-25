package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{

    @PersistenceContext
    private EntityManager em;

    public UserRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public User getUserById(int id) {
        return em.find(User.class, id);
    }

    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public User getUserByUsername(String username) {
        TypedQuery<User> query = (em.createQuery("SELECT user FROM  User user Join fetch  user.roles WHERE  user.username=:username", User.class));
        query.setParameter("username", username);
        return query.getSingleResult();
       // return (User) em.createQuery("select us from User us where us.username = ?1").setParameter(1, username).getSingleResult();
    }

    @Override
    public List<User> getAllUsers() {
        return em.createQuery("select us from User us").getResultList();
    }

    @Override
    public void remove(int id) {
        em.remove(getUserById(id));
    }

    @Override
    public void update(int id, User user) {
        em.merge(user);
    }

}
