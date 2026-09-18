package com.example.arizonatweetbot.repository;

import com.example.arizonatweetbot.model.EngagementStats;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EngagementRepository extends MongoRepository<EngagementStats, String> {
}
