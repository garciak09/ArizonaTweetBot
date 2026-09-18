package com.example.arizonatweetbot.service.scheduler;

import com.example.arizonatweetbot.domain.GameInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

public class ScheduleLoader {

    public static List<GameInfo> load() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules(); // enables JavaTimeModule

            InputStream is = ScheduleLoader.class.getResourceAsStream("/arizona_schedule.json");
            return mapper.readValue(is, new TypeReference<List<GameInfo>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to load schedule", e);
        }
    }
}
