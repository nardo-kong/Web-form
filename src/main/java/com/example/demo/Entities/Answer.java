package com.example.demo.Entities;

import javax.persistence.*;

@Entity(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long answerRecordId;

    @ManyToOne
    private User user;

    @ManyToOne
    private Question question;

    @ManyToOne
    private Option option;

    // getters and setters
    public void setUser(User user) {
        this.user = user;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public void setAnswerRecordId(Long answerRecordId) {
        this.answerRecordId = answerRecordId;
    }

    public Long getAnswerRecordId() {
        return answerRecordId;
    }

}