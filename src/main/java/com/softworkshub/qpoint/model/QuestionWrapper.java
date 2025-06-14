package com.softworkshub.qpoint.model;


import lombok.Data;

import java.util.List;

@Data
public class QuestionWrapper {


    private List<Question> questions;

    public QuestionWrapper(List<Question> questions) {
        this.questions = questions;
    }

    public QuestionWrapper() {}
}
