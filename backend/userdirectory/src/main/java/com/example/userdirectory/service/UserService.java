package com.example.userdirectory.service;

import com.example.userdirectory.dto.UserDto;
import com.example.userdirectory.model.RolesEnum;
import com.example.userdirectory.model.User;
import com.example.userdirectory.model.UserNotFoundException;
import com.example.userdirectory.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository repo) {
        this.userRepository = repo;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        userValidation(user);
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updated) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updated.getName());
                    user.setEmail(updated.getEmail());
                    return userRepository.save(user);
                })
                .orElseThrow();
    }

    @SneakyThrows
    public User updateUserRole( UserDto userDto) {
        return userRepository.findById(userDto.id())
                .map(user -> {
                    user.setRole(
                            Arrays.stream(RolesEnum.values())
                                    .filter(role -> role.name().equalsIgnoreCase(userDto.role()))
                                    .findFirst()
                                    .orElseThrow(() -> new IllegalArgumentException("Invalid role: " + userDto.role()))
                    );
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException("User doesn't exists"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private void userValidation(User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
    }
}
