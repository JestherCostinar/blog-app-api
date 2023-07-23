package com.jesthercostinar.blog.services.impl;

import com.jesthercostinar.blog.dto.UserDto;
import com.jesthercostinar.blog.entities.User;
import com.jesthercostinar.blog.exceptions.ResourceNotFoundException;
import com.jesthercostinar.blog.mapper.UserMapper;
import com.jesthercostinar.blog.repositories.UserRepository;
import com.jesthercostinar.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto userdto) {
        User user = UserMapper.mapToUser(userdto);
        User savedUser = userRepository.save(user);

        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User savedUser = userRepository.save(user);

        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        return UserMapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map((user) -> UserMapper.mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.deleteById(user.getId());
    }
}
