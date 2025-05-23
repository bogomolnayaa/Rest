package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    @Override
    List<User> findAll();

    @Override
    Optional<User> findById(Integer id);

    @Override
    <S extends User> S saveAndFlush(S user);

    @Override
    void deleteById(Integer integer);
}
