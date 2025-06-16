package com.softworkshub.qpoint.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "userdb")
public class UserEntity {

    @Id
    private String id;

    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "email must not be blank")
    @Email(message = "Provide a valid email")
    private String email;

    @NotBlank(message = "Password should not be blank")
    private String password;

    private String role = "ROLE_USER";
}
