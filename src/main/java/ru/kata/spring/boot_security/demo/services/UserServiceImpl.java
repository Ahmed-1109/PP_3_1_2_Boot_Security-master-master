package ru.kata.spring.boot_security.demo.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.addUser(user);
    }

    @Transactional
    @Override
    public void removeUser(Long id) {
        userRepository.removeUser(id);
    }

    @Transactional
    @Override
    public void updateUser(User user, BindingResult bindingResult) {
        Optional<User> optUser = getUserById(user.getId());

        if (optUser.isPresent() && (!user.getPassword().equals(optUser.get().getPassword()))) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.updateUser(user);
    }

    @Override
    public BindingResult checkUsername(BindingResult bindingResult, User user) {
        Optional<User> optUser = findByUserName(user.getUsername());

        if (optUser.isPresent()) {
            if (Objects.nonNull(user.getId()) && !user.getId().equals(optUser.get().getId())) {
                bindingResult.rejectValue("email", "",
                        "Пользователь с таким email уже существует");
            } else if (Objects.isNull(user.getId())) {
                bindingResult.rejectValue("email", "",
                        "Пользователь с таким email уже существует");
            }
        }
        return bindingResult;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public Optional<User> findByUserName(String email) {
        return userRepository.findByUserName(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = findByUserName(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return user.get();
    }
}
