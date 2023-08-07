package com.jesthercostinar.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jesthercostinar.blog.controllers.UserController;
import com.jesthercostinar.blog.dto.UserDto;
import com.jesthercostinar.blog.entities.User;
import com.jesthercostinar.blog.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private UserDto user;
    private UserDto admin;

    @BeforeEach
    void init() {
        user = new UserDto(1,
                "juan",
                "juan@gmail.com",
                "#Ga2gi8go",
                "to follow...");

        admin = new UserDto(2,
                "admin",
                "admin@gmail.com",
                "ga2gi8go",
                "to follow...");
    }

    @Test
    void test_createUser() throws Exception {
        // Arrange
        when(userService.createUser(any(UserDto.class))).thenReturn(user);

        // Act and Assert
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("juan@gmail.com"));
    }

    @Test
    void test_getAllUsers() throws Exception {
        // Arrange
        List<UserDto> users = new ArrayList<>();
        users.add(user);
        users.add(admin);

        when(userService.getAllUsers()).thenReturn(users);

        // Act and Assert
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(users.size()));
    }

    @Test
    void test_getUserById() throws Exception {
        // Arrange
        when(userService.getUserById(anyInt())).thenReturn(user);

        // Act and Assert
        mockMvc.perform(get("/api/users/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("juan@gmail.com"));
    }

    @Test
    void test_updateUser() throws Exception {
        // Arrange
        when(userService.updateUser(any(UserDto.class), anyInt())).thenReturn(user);

        // Assert
        mockMvc.perform(put("/api/users/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    void test_deleteUser() throws Exception {
        // Arrange
        doNothing().when(userService).deleteUser(anyInt());

        // Assert
        mockMvc.perform(delete("/api/users/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void test_createUser_WithInvalidData_ReturnsBadRequest() throws Exception {
        // Arrange
        user.setName("");
        when(userService.createUser(any(UserDto.class))).thenReturn(user);

        // Act & Assert
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }
}
