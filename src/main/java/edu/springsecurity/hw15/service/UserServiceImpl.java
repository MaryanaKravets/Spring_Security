package edu.springsecurity.hw15.service;

import edu.springsecurity.hw15.exception.Message;
import edu.springsecurity.hw15.exception.NotFoundException;
import edu.springsecurity.hw15.model.Role;
import edu.springsecurity.hw15.model.User;
import edu.springsecurity.hw15.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService, Message {

    private final UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        userRepository.save(new User(user.getUsername(), user.getEmail(), user.getPassword(), roles));
    }

    @Override
    public User updateUser(User user) {

        return userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> findAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(()-> new  NotFoundException(String.format(USER_NOT_FOUND_EXCEPTION_MESSAGE,id)));
    }

    @Override
    public User getByUsername(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(()->new NotFoundException(String.format(USERNAME_NOT_FOUND_EXCEPTION_MESSAGE,username)));
    }

    @Override
    public boolean existsByUsername(String username) {

        return userRepository.existsByUsername(username);
    }

    @Override
    public void deleteByUsername(String username) {

        userRepository.deleteByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).get();

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(),
                true, true, true, user.getAuthorities());
    }
}