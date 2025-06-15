package com.softworkshub.qpoint.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "question")
public class Question {

//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private String id;

    private String question;

    private String subject;

    private String term;

    private Integer year;
    private String imageUrl;

}
