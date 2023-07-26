package com.jesthercostinar.blog.repository;

import com.jesthercostinar.blog.entities.User;
import com.jesthercostinar.blog.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;
    private User admin;

    @BeforeEach
    void init() {
        user = new User();
        user.setName("Juan");
        user.setEmail("juan@gmail.com");
        user.setPassword("juan123");
        user.setAbout("to follow...");

        admin = new User();
        admin.setName("Admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("admin123");
        admin.setAbout("to follow...");
    }

    @Test
    @DisplayName("Test if User save to database")
    void test_saveUser() {
        // Arrange - Set up the data

        // Act - Calling the method to test
        User savedUser = userRepository.save(user);

        // Assert - Verify if correct or not
        assertNotNull(savedUser);
        assertThat(savedUser.getId(), is(notNullValue()));
    }

    @Test
    @DisplayName("Test to get all users")
    void test_findAllUsers() {
        // Arrange
        userRepository.save(user);
        userRepository.save(admin);

        // Act
        List<User> users = userRepository.findAll();

        // Assert
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    @DisplayName("Test to find user by id")
    void test_findUserById() {
        // Arrange
        userRepository.save(user);

        // Act
        User existingUser = userRepository.findById(user.getId()).get();

        // Assert
        assertNotNull(existingUser);
        assertEquals("juan@gmail.com", user.getEmail());
    }

    @Test
    @DisplayName("Test to update user")
    void test_updateUser() {
        // Arrange
        userRepository.save(user);
        User existingUser = userRepository.findById(user.getId()).get();

        // Act
        existingUser.setName("Delfin");
        userRepository.save(existingUser);

        // Assert
        assertEquals("Delfin", existingUser.getName());
        assertEquals("juan@gmail.com", existingUser.getEmail());
    }

    @Test
    @DisplayName("Test deleted user")
    void test_deleteUserById() {
        // Arrange
        userRepository.save(user);
        User existingUser = userRepository.findById(user.getId()).get();

        // Act
        userRepository.deleteById(existingUser.getId());

        // Assert
        assertFalse(userRepository.findById(user.getId()).isPresent());
    }


//     TODO :: Use this test to test login
//    @Test
//    void test_findByEmail() {
//        // Arrange - Set up the data
//
//        // Act - Method to test
//        User existingUser = userRepository.findUserByEmail(user.getEmail());
//
//        // Assert - Verify the test
//        assertNotNull(existingUser);
//        assertEquals("juan@gmail.com", existingUser.getEmail());
//        assertEquals("Juan", existingUser.getName());
//    }

    @Test
    void test_findByEmail_NonExistingEmail() {
        // Arrange - Set up the data

        // Act - Calling method to test
        User existingUser = userRepository.findUserByEmail("juan@gmail.com");

        // Assert - Verify the test
        assertNull(existingUser);
    }

    @AfterEach
    void cleanupTestData() {
        userRepository.deleteAll();
    }
}
