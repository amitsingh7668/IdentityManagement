package com.example.userdirectory.controller;

import com.example.userdirectory.dto.UserDto;
import com.example.userdirectory.model.User;
import com.example.userdirectory.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
    private final UserService userService;

    public UserController(UserService svc) {
        this.userService = svc;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody UserDto user) {
        return userService.createUser(User.builder().name(user.name()).email(user.email()).build());
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/roleUpdate/{id}")
    public void updateRole(@RequestBody UserDto userDto) {
        userService.updateUserRole(userDto);
    }
}
