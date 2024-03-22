package com.example.demo.Services;
import com.example.demo.Daos.AnswerRecordDao;
import com.example.demo.Entities.AnswerRecord;

import org.springframework.stereotype.Service;


@Service
public class AnswerRecordService {
    private final AnswerRecordDao answerRecordDao;

    public AnswerRecordService(AnswerRecordDao answerRecordDao) {
        this.answerRecordDao = answerRecordDao;
    }

    public AnswerRecord findById(Long id) {
        return answerRecordDao.findById(id).orElse(null);
    }

    public void saveAnswerRecord(AnswerRecord answerRecord) {
        answerRecordDao.save(answerRecord);
    }

    public void completeAnswerRecord(AnswerRecord answerRecord) {
        answerRecord.setCompleted(true);
        answerRecordDao.save(answerRecord);
    }
}
