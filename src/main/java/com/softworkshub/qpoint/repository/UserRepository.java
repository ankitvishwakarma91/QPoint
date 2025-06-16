package com.softworkshub.qpoint.repository;


import com.softworkshub.qpoint.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    public UserEntity findByUsername(String username);
    public Boolean existsByUsername(String username);
    public Boolean existsByEmail(String email);
}
