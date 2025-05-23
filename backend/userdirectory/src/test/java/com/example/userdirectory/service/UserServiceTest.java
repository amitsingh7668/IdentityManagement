package com.example.userdirectory.service;

import com.example.userdirectory.dto.UserDto;
import com.example.userdirectory.model.RolesEnum;
import com.example.userdirectory.model.User;
import com.example.userdirectory.model.UserNotFoundException;
import com.example.userdirectory.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setName("Singh");
        user.setEmail("singh@google.com");
        user.setRole(RolesEnum.ADMIN);
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<User> result = userService.getAllUsers();
        assertEquals(1, result.size());
        assertEquals("Singh", result.get(0).getName());
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(user)).thenReturn(user);
        User created = userService.createUser(user);
        assertEquals("Singh", created.getName());
    }

    @Test
    void testUpdateUserSuccess() {
        User updatedUser = new User();
        updatedUser.setName("Jane");
        updatedUser.setEmail("jane@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User result = userService.updateUser(1L, updatedUser);
        assertEquals("Jane", result.getName());
        assertEquals("jane@example.com", result.getEmail());
    }

    @Test
    void testUpdateUserRoleSuccess() {
        UserDto dto = new UserDto(1L, "Singh", "singh@google.com", "ADMIN");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User result = userService.updateUserRole(dto);
        assertEquals(RolesEnum.ADMIN, result.getRole());
    }

    @Test
    void testUpdateUserRoleInvalidRole() {
        UserDto dto = new UserDto(1L, "Singh", "singh@google.com", "UNKNOWN");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUserRole(dto);
        });

        assertTrue(exception.getMessage().contains("Invalid role"));
    }

    @Test
    void testUpdateUserRoleUserNotFound() {
        UserDto dto = new UserDto(99L, "Ghost", "ghost@example.com", "ADMIN");

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.updateUserRole(dto);
        });

        assertTrue(exception.getMessage().contains("User doesn't exists"));
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
