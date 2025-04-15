package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserDetails;
import ru.kata.spring.boot_security.demo.dao.UserRepository;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository usersRepository;

    @Autowired
    public UserDetailService(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        UserDetails userDetails = new UserDetails(user.get());
        userDetails.getAuthorities().size();
        return userDetails;
    }
}
