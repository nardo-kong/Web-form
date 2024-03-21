package com.example.demo.Services;
import com.example.demo.Daos.AnswerDao;
import com.example.demo.Entities.Answer;
import com.example.demo.Entities.Option;
import com.example.demo.Entities.Question;
import com.example.demo.Entities.User;
import com.example.demo.Entities.Scale;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AnswerService {
    // Inject the DAOs
    private final AnswerDao answerDao;

    public AnswerService(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }

    public void saveAnswers(User user, Scale scale, Map<Question, Option> answers) {
        for (Map.Entry<Question, Option> entry : answers.entrySet()) {
            Question question = entry.getKey();
            Option option = entry.getValue();

            Answer answer = new Answer();
            answer.setUser(user);
            answer.setQuestion(question);
            answer.setOption(option);

            answerDao.save(answer);
        }
    }
}