package com.example.dud_mobile.models.lessons.reading;

import com.example.dud_mobile.models.lessons.Question;

public class ReadingQuestion extends Question {

    public ReadingQuestion(int id, String question, String option1, String option2, String option3, int correctOption) {
        super(id, question, option1, option2, option3, correctOption);
    }
}
