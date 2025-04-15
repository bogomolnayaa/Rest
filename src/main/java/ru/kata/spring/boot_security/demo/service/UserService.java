package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> index();
    Optional<User> show(int id);
    void save(User person);
    void update(int id, User updatedPerson);
    void delete(int id);

    User findByUsername(String name);

}
