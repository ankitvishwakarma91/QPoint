package com.softworkshub.qpoint.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String question;

    private String subject;

    private String term;

    private Integer year;
    private String imageUrl;

}
