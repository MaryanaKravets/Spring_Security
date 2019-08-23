package edu.springsecurity.hw15.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.*;

@Entity
@Data
@Table(name = "users")
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    @Email
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Users_Role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    Set<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String ROLE_PREFIX = "ROLE_";
        List<GrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
        }
        return list;
    }

    @JsonBackReference
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonBackReference
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonBackReference
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonBackReference
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonBackReference
    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(String username, @Email String email, String password, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User() {
    }

    ;
}
