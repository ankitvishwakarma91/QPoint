package com.softworkshub.qpoint.service;

import com.softworkshub.qpoint.model.Question;
import com.softworkshub.qpoint.model.QuestionWrapper;

import java.util.List;

public interface QuestionService {

    public List<Question> getQuestion(String subject, Integer year);

    public void addQuestion(Question question);

    public void addAllQuestions(List<Question> questionWrapper);
}
