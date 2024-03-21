package com.example.demo.Daos;
import com.example.demo.Entities.Answer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerDao extends JpaRepository<Answer, Long> {
}