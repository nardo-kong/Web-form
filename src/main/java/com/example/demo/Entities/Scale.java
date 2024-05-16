package com.example.demo.Entities;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "scales")
public class Scale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int totalPage;

    private boolean isDeleted;

    private LocalDateTime createdDate;

    private LocalDateTime expiryDate;

    private Long previousId;

    @OneToMany(mappedBy = "scale")
    private List<Question> questions;

    // getters and setters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public Long getPreviousId() {
        return previousId;
    }
    
}