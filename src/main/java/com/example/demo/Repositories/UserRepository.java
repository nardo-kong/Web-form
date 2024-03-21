package com.example.demo.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.User;

public interface UserRepository extends JpaRepository<User, String> {
    User findByAccountId(String accountId);
}