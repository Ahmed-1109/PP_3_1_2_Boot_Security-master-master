package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.configs.PasswordConfig;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private final PasswordConfig passwordConfig;


    public UserServiceImpl(UserRepository userRepository,  PasswordConfig passwordConfig) {
        this.userRepository = userRepository;
        this.passwordConfig = passwordConfig;

    }

    @Override
    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    @Override
    @Transactional
    public void save(User user) {
       user.setPassword(passwordConfig.passwordEncoder().encode(user.getPassword()));
       userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username);
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    @Transactional
    public void update(int id, User user) {
        User user1 = getUserById(user.getId());
        if (!user1.getPassword().equals(user.getPassword())){
        user.setPassword(passwordConfig.passwordEncoder().encode(user.getPassword()));
        }
        userRepository.update(id,user);
    }

    @Override
    @Transactional
    public void removeUser(int id) {
        userRepository.remove(id);
    }
}
