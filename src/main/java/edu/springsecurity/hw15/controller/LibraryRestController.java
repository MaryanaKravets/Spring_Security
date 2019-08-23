package edu.springsecurity.hw15.controller;

import edu.springsecurity.hw15.model.User;
import edu.springsecurity.hw15.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
public class LibraryRestController {

    IUserService iUserService;

    @ResponseBody
    @GetMapping("/user")
    public List<User> findAllUser() {

        return iUserService.findAllUsers();
    }


    @ResponseBody
    @PostMapping("/user")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        iUserService.saveUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }


    @ResponseBody
    @DeleteMapping("/user/{username}")
    public void deleteUser(@PathVariable("username") String username) {
        iUserService.deleteByUsername(username);
    }


    @ResponseBody
    @PatchMapping("/user")
    public User updateUser(@RequestBody User user) {
        return iUserService.updateUser(user);
    }


    @ResponseBody
    @GetMapping("/user/id/{id}")
    public Optional<User> getUserById(@PathVariable("id") Long id) {
        return iUserService.getUserById(id);
    }

    @ResponseBody
    @GetMapping("/user/{username}")
    public Optional<User> findByUsername(@PathVariable("username") String username) {
        return iUserService.findByUsername(username);
    }


    @ResponseBody
    @GetMapping("/user/exist/{username}")
    public boolean existByUsername(@PathVariable("username") String username) {
        return iUserService.existsByUsername(username);
    }
}


