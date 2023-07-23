package com.jesthercostinar.blog.controllers;

import com.jesthercostinar.blog.dto.ApiResponse;
import com.jesthercostinar.blog.dto.UserDto;
import com.jesthercostinar.blog.entities.User;
import com.jesthercostinar.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST Mapping - Create
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    // GET Mapping - Get User Data
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // PUT Mapping - Update User
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user, @PathVariable Integer id) {
        return ResponseEntity.ok(userService.updateUser(user, id));
    }

    // DELETE MAPPING - Delete
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);

        return new ResponseEntity(new ApiResponse("User has been successfully deleted!", true), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
