package com.softworkshub.qpoint.service;

import com.softworkshub.qpoint.model.FeedBack;
import com.softworkshub.qpoint.model.Question;
import com.softworkshub.qpoint.model.QuestionWrapper;

import java.util.List;

public interface QuestionService {

    public List<Question> getQuestion(String subject, Integer year,int pageSize,int pageNo);

    public void addQuestion(Question question);

    public void addAllQuestions(List<Question> questionWrapper);

    public long countBySubjectAndYear(String subject, Integer year);

    public Question addSingleQuestionUsingApi(Question question);

    public FeedBack addFeedback(FeedBack feedBack);

    public List<FeedBack> getFeedback();
}
