package com.jesthercostinar.blog.integration;

import com.jesthercostinar.blog.dto.UserDto;
import com.jesthercostinar.blog.entities.User;
import com.jesthercostinar.blog.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public static void init() {
        restTemplate =  new RestTemplate();
    }
    private UserDto user, admin;

    @BeforeEach
    public void beforeSetup() {
        baseUrl = baseUrl + ":" + port + "/api/users";

        user = new UserDto(1,
                "juan",
                "juan@gmail.com",
                "#Ga2gi8go",
                "to follow...");

        admin = new UserDto(2,
                "admin",
                "admin@gmail.com",
                "$Ga2gi8go",
                "to follow...");
    }

    @Test
    void test_createUser() {
        User savedUser = restTemplate.postForObject(baseUrl, user, User.class);

        assertNotNull(savedUser);
    }

    @Test
    void test_getAllUsers() {
        List<UserDto> users = new ArrayList<>();
        users.add(user);
        users.add(admin);
        users.forEach(user -> {
            restTemplate.postForObject(baseUrl, user, User.class);
        });

        // Act
        List<UserDto> fetchUser = restTemplate.getForObject(baseUrl, List.class);

        // Assert
        assertEquals(2, fetchUser.size());
    }

    @Test
    void test_getUserById() {
        // Arrange
        User fetchUser = restTemplate.postForObject(baseUrl, user, User.class);

        // Act
        UserDto existingUser = restTemplate.getForObject(baseUrl + "/" + fetchUser.getId(), UserDto.class);

        // Assert
        assertNotNull(existingUser);
        assertEquals("juan@gmail.com", existingUser.getEmail());
    }

    @Test
    void test_updateUser() {
        // Arrange
        User savedUser = restTemplate.postForObject(baseUrl, user, User.class);

        // Act
        savedUser.setEmail("edited@gmail.com");
        restTemplate.put(baseUrl + "/{id}", savedUser, savedUser.getId());

        User existingUser = restTemplate.getForObject(baseUrl + "/" + savedUser.getId(), User.class);

        // Assert
        assertNotNull(existingUser);
        assertEquals("edited@gmail.com", existingUser.getEmail());
    }

    @Test
    void test_deleteUser() {
        // Arrange
        User savedUser = restTemplate.postForObject(baseUrl, user, User.class);

        // Act
        restTemplate.delete(baseUrl + "/" + savedUser.getId());
        int userCount = userRepository.findAll().size();

        // Assert
        assertEquals(0, userCount);
    }

//    @Test
//    void test_createUser_WithInvalidData_ReturnBadRequest() {
//        // Arrange - set value for the testing
//        user.setName("");
//
//        // Act
//        ResponseEntity<User> invalidUser = restTemplate.postForEntity(baseUrl, user, User.class);
//
//        // Assert
//        assertEquals(HttpStatus.BAD_REQUEST, invalidUser.getStatusCode());
//        assertNull(invalidUser.getBody());
//    }

    @AfterEach
    public void afterSetup() {
        userRepository.deleteAll();
    }
}
