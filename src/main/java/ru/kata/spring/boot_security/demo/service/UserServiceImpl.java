package ru.kata.spring.boot_security.demo.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        }

        @Transactional(readOnly = true)
        @Override
        public List<User> getAllUsers() {
            return userRepository.findAll();
        }

        @Transactional(readOnly = true)
        @Override
        public User getUser(long id) {
            User user = userRepository.findById(id).orElse(null);
            Hibernate.initialize(user.getRoles());
            return user;
        }

        @Transactional
        @Override
        public void addUser(User user) {
            String encode = passwordEncoder.encode(user.getPassword());
            user.setPassword(encode);
            userRepository.save(user);
        }

        @Transactional
        @Override
        public void updateUser(User user) {
            String encode = passwordEncoder.encode(user.getPassword());
            user.setPassword(encode);
            userRepository.save(user);
        }

        @Transactional
        @Override
        public void removeUser(long id) {
            userRepository.deleteById(id);
        }
}
