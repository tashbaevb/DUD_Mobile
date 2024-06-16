package com.example.dud_mobile.models.lessons.qa;

import com.google.gson.annotations.SerializedName;

public class CheckAnswer {

    @SerializedName("selectedOption")
    private int selectedOption;

    public CheckAnswer(int selectedOption) {
        this.selectedOption = selectedOption;
    }

    public int getSelectedOption() {
        return selectedOption;
    }
}
