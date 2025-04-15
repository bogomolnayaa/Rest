package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;

import java.util.Set;

@Component
public class RoleServiceImpl implements RoleService {

    private final RoleRepository rolesRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }


    @Override
    public Set<Role> getAllRoles() {
        return rolesRepository.getSetOfRoles();
    }
}
