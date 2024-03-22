package com.example.demo.Entities;

import javax.persistence.*;

@Entity(name = "answer_records")
public class AnswerRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Scale scale;

    private boolean completed;

    // Other fields...

    // Getters and setters...
    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }

    public Scale getScale() {
        return scale;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }
}
