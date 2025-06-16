package com.softworkshub.qpoint.service;


import com.softworkshub.qpoint.model.UserEntity;
import com.softworkshub.qpoint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public String signUp(UserEntity user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userEntity);
        return "User successfully registered";
    }


    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
