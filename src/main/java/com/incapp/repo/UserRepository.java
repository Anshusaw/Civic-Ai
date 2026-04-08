package com.incapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
