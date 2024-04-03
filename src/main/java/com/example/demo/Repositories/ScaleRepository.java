package com.example.demo.Repositories;
import com.example.demo.Entities.Scale;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScaleRepository extends JpaRepository<Scale, Long> {
    Scale findByTitle(String title);
}
