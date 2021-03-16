package com.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
