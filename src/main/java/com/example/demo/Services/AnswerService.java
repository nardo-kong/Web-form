package com.example.demo.Services;
import com.example.demo.Daos.AnswerDao;
import com.example.demo.Daos.AnswerRecordDao;
import com.example.demo.Entities.Answer;
import com.example.demo.Entities.AnswerRecord;
import com.example.demo.Entities.Question;
import com.example.demo.Entities.User;
import com.example.demo.Entities.Scale;

import org.springframework.stereotype.Service;

import java.util.Date;

import javax.transaction.Transactional;

@Service
public class AnswerService {
    // Inject the DAOs
    private final AnswerDao answerDao;
    private final AnswerRecordDao answerRecordDao;

    public AnswerService(AnswerDao answerDao, AnswerRecordDao answerRecordDao) {
        this.answerDao = answerDao;
        this.answerRecordDao = answerRecordDao;
    }

    @Transactional
    public AnswerRecord starOrContinueAnswerRecord(User user, Scale scale) {
        AnswerRecord answerRecord = answerRecordDao.findByUserAndScale(user, scale);

        if (answerRecord == null) {
            answerRecord = new AnswerRecord();
            answerRecord.setUser(user);
            answerRecord.setScale(scale);
            answerRecordDao.save(answerRecord);
        }

        return answerRecord;
    }

    @Transactional
    public void saveAnswers(AnswerRecord answerRecord, Question question, String answerContent) {
        Answer answer = new Answer();
        answer.setAnswerRecordId(answerRecord.getId());
        answer.setQuestion(question);
        answer.setAnswerContent(answerContent);
        answerDao.save(answer);
        
    }

    @Transactional
    public void completeAnswerRecord(AnswerRecord answerRecord) {
        answerRecord.setCompleted(true);
        answerRecordDao.save(answerRecord);
        answerRecord.setFinishTimestamp(new Date());
    }
}