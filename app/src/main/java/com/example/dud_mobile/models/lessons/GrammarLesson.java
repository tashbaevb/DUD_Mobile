package com.example.dud_mobile.models.lessons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GrammarLesson {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("imgPath")
    @Expose
    String grammarImage;

    public GrammarLesson(int id, String title, String description, String grammarImage) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.grammarImage = grammarImage;
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

    public String getGrammarImage() {
        return grammarImage;
    }

    public void setGrammarImage(String grammarImage) {
        this.grammarImage = grammarImage;
    }
}
