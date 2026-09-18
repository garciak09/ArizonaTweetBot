package com.example.arizonatweetbot.repository;

import com.example.arizonatweetbot.model.GameInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameInfoRepository extends MongoRepository<GameInfo, String> {
}
