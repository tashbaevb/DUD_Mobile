package com.example.dud_mobile.models.lessons;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReadingLesson {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("questions")
    private List<ReadingQuestion> questions;


    public ReadingLesson(int id, String title, String description, List<ReadingQuestion> questions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ReadingQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ReadingQuestion> questions) {
        this.questions = questions;
    }
}
