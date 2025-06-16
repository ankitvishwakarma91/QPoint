package com.softworkshub.qpoint.service;

import com.softworkshub.qpoint.model.LoginDto;
import com.softworkshub.qpoint.model.UserEntity;

public interface UserService {

    public String signUp(UserEntity user);
    public UserEntity getUserByUsername(String username);

    public boolean logout();

    public Boolean existsByUsername(String username);

    public Boolean existsByEmail(String email);
}
