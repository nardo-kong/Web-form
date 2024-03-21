package com.example.demo.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Scale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "scale")
    private List<Question> questions;

    // getters and setters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}