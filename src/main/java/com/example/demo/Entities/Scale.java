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

    private String eGroupBgColor;

    private String cGroupBgColor;

    private String eGroupMusic;

    private String cGroupMusic; 

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

    public String getEGroupBgColor() {
        return eGroupBgColor;
    }

    public String getCGroupBgColor() {
        return cGroupBgColor;
    }

    public String getEGroupMusic() {
        return eGroupMusic;
    }

    public String getCGroupMusic() {
        return cGroupMusic;
    }
    
}