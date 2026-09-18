package com.example.arizonatweetbot.service.scheduler;

import com.example.arizonatweetbot.domain.GameInfo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleScraper scraper;

    private List<GameInfo> schedule;

    @PostConstruct
    public void load() {
        this.schedule = scraper.fetchSchedule();
    }

    public GameInfo getCurrentGame() {
        LocalDateTime now = LocalDateTime.now();

        return schedule.stream()
                .filter(g -> {
                    LocalDateTime start = g.getStartTime();
                    LocalDateTime end = start.plusHours(3);
                    return now.isAfter(start) && now.isBefore(end);
                })
                .findFirst()
                .orElse(null);
    }

    public GameInfo getUpcomingGame() {
        LocalDateTime now = LocalDateTime.now();

        return schedule.stream()
                .filter(g -> {
                    LocalDateTime start = g.getStartTime();
                    return now.isBefore(start) && now.plusHours(2).isAfter(start);
                })
                .findFirst()
                .orElse(null);
    }
}
