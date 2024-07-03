package com.example.demo.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String text;

    private String type;

    @ManyToOne
    @JoinColumn(name = "scale_id")
    private Scale scale;

    @OneToMany
    @JoinColumn(name = "question_id")
    private List<Option> options;

    private int page;

    private String imageUrl;

    //getters and setters

    public int getId() {
        return id;
    }

    public String getText(){
        return text;
    }

    public String getType() {
        return type;
    }

    public Scale getScale() {
        return scale;
    }

    public List<Option> getOptions() {
        return options;
    }

    public int getPage() {
        return page;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}