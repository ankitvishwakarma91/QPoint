package com.softworkshub.qpoint.service;


import com.softworkshub.qpoint.model.FeedBack;
import com.softworkshub.qpoint.model.Question;
import com.softworkshub.qpoint.repository.FeedBackRepo;
import com.softworkshub.qpoint.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {


    private QuestionRepository questionRepository;
    private FeedBackRepo feedBackRepo;

    @Autowired
    public QuestionServiceImpl questionServiceImpl(QuestionRepository questionRepository, FeedBackRepo feedBackRepo){
        this.questionRepository = questionRepository;
        this.feedBackRepo = feedBackRepo;
        return this;
    }

    @Override
    public Question addSingleQuestionUsingApi(Question question) {
        return questionRepository.save(question);
    }



    @Override
    public List<Question> getQuestion(
            String subject,
            Integer year,
            int pageSize,
            int pageNo
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return questionRepository.findByYearAndSubject(year, subject, pageable);
    }

    @Override
    public void addQuestion(Question question) {
        questionRepository.save(question);
    }

    @Override
    public void addAllQuestions(List<Question> questions) {
       questionRepository.saveAll(questions);
    }

    @Override
    public long countBySubjectAndYear(String subject, Integer year) {
        return questionRepository.countBySubjectAndYear(subject, year);
    }

    @Override
    public FeedBack addFeedback(FeedBack feedBack) {
        return feedBackRepo.save(feedBack);
    }

    @Override
    public List<FeedBack> getFeedback() {
        return feedBackRepo.findAll();
    }
}
