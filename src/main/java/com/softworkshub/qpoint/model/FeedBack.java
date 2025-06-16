package com.softworkshub.qpoint.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "feedback")
public class FeedBack {

    @Id
    private String id;

    @NotBlank(message = "Please enter your name")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Message is required")
    @Length(max = 300, message = "Message must not exceed 300 characters")
    private String message;
}
