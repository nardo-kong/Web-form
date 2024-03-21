package com.example.demo.Repositories;
import com.example.demo.Entities.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByScale(String scale);
}
