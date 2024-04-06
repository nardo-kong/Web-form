package com.example.demo.Entities;

import javax.persistence.*;

@Entity(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long answerRecordId;

    @ManyToOne
    private Question question;

    private String answerContent;

    // getters and setters

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public void setAnswerRecordId(Long answerRecordId) {
        this.answerRecordId = answerRecordId;
    }

    public Long getAnswerRecordId() {
        return answerRecordId;
    }

}