package com.jesthercostinar.blog.repositories;

import com.jesthercostinar.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByEmail(String email);

}
