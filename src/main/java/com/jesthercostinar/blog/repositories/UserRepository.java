package com.jesthercostinar.blog.repositories;

import com.jesthercostinar.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
