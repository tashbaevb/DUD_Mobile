package com.example.dud_mobile.models.lessons.listening;

import com.example.dud_mobile.models.lessons.reading.ReadingQuestion;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListeningLesson {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("mp3FilePath")
    private String mp3File;

    @SerializedName("questions")
    private List<ListeningQuestion> questions;

    public ListeningLesson(int id, String title, String description, String mp3File, List<ListeningQuestion> questions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.mp3File = mp3File;
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

    public String getMp3File() {
        return mp3File;
    }

    public void setMp3File(String mp3File) {
        this.mp3File = mp3File;
    }

    public List<ListeningQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ListeningQuestion> questions) {
        this.questions = questions;
    }
}
