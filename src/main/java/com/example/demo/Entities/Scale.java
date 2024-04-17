package com.example.demo.Entities;

import javax.persistence.*;
import java.util.List;

@Entity(name = "scales")
public class Scale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int totalPage;

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
}