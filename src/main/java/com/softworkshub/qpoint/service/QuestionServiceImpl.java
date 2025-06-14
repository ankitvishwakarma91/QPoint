package com.softworkshub.qpoint.service;


import com.softworkshub.qpoint.model.Question;
import com.softworkshub.qpoint.model.QuestionWrapper;
import com.softworkshub.qpoint.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> getQuestion(String subject, Integer year) {
        return questionRepository.findByYearAndSubject(year, subject);
    }

    @Override
    public void addQuestion(Question question) {
        questionRepository.save(question);
    }

    @Override
    public void addAllQuestions(List<Question> questions) {
       questionRepository.saveAll(questions);
    }
}
