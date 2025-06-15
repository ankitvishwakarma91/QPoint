package com.softworkshub.qpoint.repository;

import com.softworkshub.qpoint.model.Question;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findByYearAndSubject(Integer year, String subject);
}
