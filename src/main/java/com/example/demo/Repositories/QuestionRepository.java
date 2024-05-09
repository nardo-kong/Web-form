package com.example.demo.Repositories;
import com.example.demo.Entities.Question;
import com.example.demo.Entities.Scale;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByScale(Scale scale);
    List<Question> findByScaleAndPage(Scale scale, int page);
    
}
