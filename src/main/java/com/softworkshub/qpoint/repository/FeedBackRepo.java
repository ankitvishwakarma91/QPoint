package com.softworkshub.qpoint.repository;

import com.softworkshub.qpoint.model.FeedBack;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackRepo extends MongoRepository<FeedBack, String> {
}
