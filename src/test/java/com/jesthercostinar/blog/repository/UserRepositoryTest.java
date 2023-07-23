package com.jesthercostinar.blog.repository;

import com.jesthercostinar.blog.entities.User;
import com.jesthercostinar.blog.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
        // Arrange - Set up the data
        User user = new User();
        user.setName("Juan");
        user.setEmail("juan@gmail.com");
        user.setPassword("juan123");
        user.setAbout("to follow...");

        // Act - Calling the method to test
        User savedUser = userRepository.save(user);

        // Assert - Verify if correct or not
        assertNotNull(savedUser);
        assertThat(savedUser.getId(), is(notNullValue()));
    }


    // TODO :: Use this test to test login
//    @Test
//    void test_findByEmail() {
//        // Arrange - Set up the data
//        User user = new User();
//        user.setName("Juan");
//        user.setEmail("juan@gmail.com");
//        user.setPassword("juan123");
//        user.setAbout("to follow...");
//
//        // Act - Method to test
//        User existingUser = userRepository.findUserByEmail(user.getEmail());
//
//        // Assert - Verify the test
//        assertNotNull(existingUser);
//        assertEquals("juan@gmail.com", user.getEmail());
//    }
//
//    @Test
//    void test_findByEmail_NonExistingEmail() {
//        // Act - Calling method to test
//        User existingUser = userRepository.findUserByEmail("juan@gmail.com");
//
//        // Assert - Verify the test
//        assertNull(existingUser);
//    }
}
