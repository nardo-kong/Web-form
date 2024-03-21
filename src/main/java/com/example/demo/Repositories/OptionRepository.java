package com.example.demo.Repositories;
import com.example.demo.Entities.Option;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}