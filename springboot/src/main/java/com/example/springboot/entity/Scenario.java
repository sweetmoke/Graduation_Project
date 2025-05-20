package com.example.springboot.entity;

public class Scenario {
    private Integer id;
    private String title;
    private String context;
    private String question;
    private String correctKeywords;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectKeywords() {
        return correctKeywords;
    }

    public void setCorrectKeywords(String correctKeywords) {
        this.correctKeywords = correctKeywords;
    }
}
