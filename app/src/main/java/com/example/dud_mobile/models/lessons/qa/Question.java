package com.example.dud_mobile.models.lessons.qa;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Question {

    @SerializedName("id")
    private int id;

    @SerializedName("question")
    private String question;

    @SerializedName("option1")
    private String option1;

    @SerializedName("option2")
    private String option2;

    @SerializedName("option3")
    private String option3;

    @SerializedName("correctOption")
    private int correctOption;

    public Question(int id, String question, String option1, String option2, String option3, int correctOption) {
        this.id = id;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.correctOption = correctOption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public int getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(int correctOption) {
        this.correctOption = correctOption;
    }

    public List<String> getOptions() {
        List<String> options = new ArrayList<>();
        options.add(option1);
        options.add(option2);
        options.add(option3);
        return options;
    }
}
