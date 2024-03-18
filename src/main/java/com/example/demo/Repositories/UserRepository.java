package com.example.demo.Repositories;
import com.example.demo.Class.User;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {
    User findByAccountId(String accountId);
}