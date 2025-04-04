package ru.kata.spring.boot_security.demo.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        }

        @Override
        public List<User> getAllUsers() {
            return userRepository.findAll();
        }

        @Override
        public User getUser(long id) {
            User user = userRepository.findById(id).orElse(null);
            Hibernate.initialize(user.getRoles());
            return user;
        }

        @Override
        public void addUser(User user) {
            userRepository.save(user);
        }

        @Transactional
        @Override
        public void updateUser(User user) {
        userRepository.save(user);
        }

        @Override
        public void removeUser(long id) {
            userRepository.deleteById(id);
        }
}
