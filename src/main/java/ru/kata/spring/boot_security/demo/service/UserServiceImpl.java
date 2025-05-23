package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.dao.UserRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Component
public class UserServiceImpl implements UserService {
    private final UserRepository usersRepository;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository usersRepository, RoleService roleService) {
        this.usersRepository = usersRepository;
        this.roleService = roleService;
    }

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional(readOnly = true)
    public List<User> index() {
        return usersRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> show(int id) {
        return usersRepository.findById(id);
    }

    @Override
    @Transactional
    public void save(User person) {
        String encode = passwordEncoder.encode(person.getPassword());
        person.setPassword(encode);

        Set<Role> copyOfRoles = new HashSet<>(person.getRoles());
        person.getRoles().clear();
        for (Role personRole : copyOfRoles) {
            personRole.setName("ROLE_" + personRole.getName());

            for (Role mainRole : roleService.getAllRoles()) {
                if (Objects.equals(mainRole.getName(), personRole.getName())) {
                    person.getRoles().add(mainRole);
                }
            }
        }
        usersRepository.saveAndFlush(person);
    }

    @Override
    @Transactional
    public void update(int id, User updatedPerson) {
        User user = usersRepository.findById(id).get();

        if (!Objects.equals(updatedPerson.getPassword(), user.getPassword())) {
            String encode = passwordEncoder.encode(updatedPerson.getPassword());
            updatedPerson.setPassword(encode);
        }

        if (!updatedPerson.getRoles().isEmpty()) {

            Set<Role> copyOfRoles = new HashSet<>(updatedPerson.getRoles());
            updatedPerson.getRoles().clear();
            for (Role personRole : copyOfRoles) {
                personRole.setName("ROLE_" + personRole.getName());

                for (Role mainRole : roleService.getAllRoles()) {
                    if (Objects.equals(mainRole.getName(), personRole.getName())) {
                        updatedPerson.getRoles().add(mainRole);
                    }
                }
            }
            user.setRoles(updatedPerson.getRoles());
        }

        user.setUsername(updatedPerson.getUsername());
        user.setAge(updatedPerson.getAge());
        user.setPassword(updatedPerson.getPassword());
        usersRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void delete(int id) {
        usersRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String name) {
        return usersRepository.findByUsername(name).get();
    }
}
