package com.example.arizonatweetbot.repository;

import com.example.arizonatweetbot.model.TweetLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TweetLogRepository extends MongoRepository<TweetLog, String> {
}
