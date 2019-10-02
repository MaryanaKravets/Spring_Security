package edu.springsecurity.hw15.service;

import edu.springsecurity.hw15.model.User;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void saveUser(User user);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    User updateUser(User user);

    @PreAuthorize("isAuthenticated()")
    List<User> findAllUsers();

    @PreAuthorize("isAuthenticated()")
    User getUserById(Long id);

    @PreAuthorize("isAuthenticated()")
    User getByUsername(String username);

    @PreAuthorize("isAuthenticated()")
    boolean existsByUsername(String username);

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteByUsername(String username);
}
