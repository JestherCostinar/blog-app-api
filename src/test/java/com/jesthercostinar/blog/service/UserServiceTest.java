package com.jesthercostinar.blog.service;

import com.jesthercostinar.blog.dto.UserDto;
import com.jesthercostinar.blog.entities.User;
import com.jesthercostinar.blog.mapper.UserMapper;
import com.jesthercostinar.blog.repositories.UserRepository;
import com.jesthercostinar.blog.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    private User user, admin;

    @BeforeEach
    void init() {
        user = new User();
        user.setId(1);
        user.setName("Juan");
        user.setEmail("juan@gmail.com");
        user.setPassword("juan123");
        user.setAbout("to follow...");

        admin = new User();
        admin.setId(2);
        admin.setName("Admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("admin123");
        admin.setAbout("to follow...");
    }

    @Test
    void test_createUser() {
        // Assert
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserDto savedUser = userService.createUser(UserMapper.mapToUserDto(user));

        // Assert
        assertNotNull(savedUser);
        assertEquals("juan@gmail.com", savedUser.getEmail());
    }

    @Test
    void test_getAllUsers() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(admin);
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<UserDto> fetchUser = userService.getAllUsers();
        // Assert
        assertEquals(2, fetchUser.size());
        assertNotNull(fetchUser);
    }

    @Test
    void test_getUserById() {
        // Arrange - Create mock data using mochita
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        // Act
        UserDto fetchUser = userService.getUserById(1);

        // Assert
        assertNotNull(fetchUser);
        assertEquals(1, fetchUser.getId());
    }

    @Test
    void test_getUserById_NotFoundException() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Assert
        assertThrows(RuntimeException.class, () -> {
            userService.getUserById(2);
        });
    }

    @Test
    void test_updateUser() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        user.setEmail("editedJuan@gmail.com");

        // Act
        userService.updateUser(UserMapper.mapToUserDto(user), user.getId());

        // Assert
        assertEquals("editedJuan@gmail.com", user.getEmail());
    }

    @Test
    void test_deleteUserById() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Act
        userService.deleteUser(user.getId());

        // Assert
        verify(userRepository, times(1)).deleteById(user.getId());
    }
}
