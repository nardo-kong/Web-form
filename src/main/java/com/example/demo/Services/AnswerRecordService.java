package com.example.demo.Services;
import com.example.demo.Daos.AnswerRecordDao;
import com.example.demo.Entities.AnswerRecord;
import com.example.demo.Entities.Scale;
import com.example.demo.Entities.User;
import com.example.demo.Repositories.ScaleRepository;
import com.example.demo.Repositories.UserRepository;

import org.springframework.stereotype.Service;


@Service
public class AnswerRecordService {
    private final AnswerRecordDao answerRecordDao;
    private final ScaleRepository scaleRepository;
    private final UserRepository userRepository;

    public AnswerRecordService(AnswerRecordDao answerRecordDao, ScaleRepository scaleRepository, UserRepository userRepository) {
        this.answerRecordDao = answerRecordDao;
        this.scaleRepository = scaleRepository;
        this.userRepository = userRepository;
    }

    public AnswerRecord findById(Long id) {
        return answerRecordDao.findById(id).orElse(null);
    }

    public AnswerRecord saveAnswerRecord(AnswerRecord answerRecord, String scaleTitle, String accountId) {
        Scale scale = scaleRepository.findByTitle(scaleTitle);
        answerRecord.setScale(scale);
        User user = userRepository.findByAccountId(accountId);
        answerRecord.setUser(user);

        AnswerRecord savedAnswerRecord = answerRecordDao.save(answerRecord);
        return savedAnswerRecord;
    }

    public AnswerRecord savePage(AnswerRecord answerRecord, int page) {
        answerRecord.setCurrentpage(page);
        return answerRecordDao.save(answerRecord);
    }

    public void completeAnswerRecord(AnswerRecord answerRecord) {
        answerRecord.setCompleted(true);
        answerRecordDao.save(answerRecord);
    }
}
