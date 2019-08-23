package edu.springsecurity.hw15.service;

import edu.springsecurity.hw15.model.Role;
import edu.springsecurity.hw15.model.User;
import edu.springsecurity.hw15.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements IUserService, UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        User user1 = new User(user.getUsername(), user.getEmail(), user.getPassword(), roles);
        userRepository.save(user1);
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
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id).isPresent() ? userRepository.findById(id) : Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username).isPresent() ? userRepository.findByUsername(username) : Optional.empty();
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