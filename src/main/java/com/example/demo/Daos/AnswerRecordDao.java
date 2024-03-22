package com.example.demo.Daos;
import com.example.demo.Entities.AnswerRecord;
import com.example.demo.Entities.User;
import com.example.demo.Entities.Scale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRecordDao extends JpaRepository<AnswerRecord, Long> {
    AnswerRecord findByUserAndScale(User user, Scale scale);
}
