package com.example.arizonatweetbot.service.scheduler;

import com.example.arizonatweetbot.domain.GameContext;
import com.example.arizonatweetbot.domain.TweetCategory;
import com.example.arizonatweetbot.domain.TweetCategoryDecider;
import com.example.arizonatweetbot.domain.GameInfo;
import com.example.arizonatweetbot.service.ai.AIContentService;
import com.example.arizonatweetbot.service.posting.PostingService;
import com.example.arizonatweetbot.service.score.LiveScoreScraper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class DailyScheduler {

    private final AIContentService aiContentService;
    private final PostingService postingService;
    private final ScheduleService scheduleService;
    private final LiveScoreScraper scoreScraper;

    @PostConstruct
    public void fireStartupTweet() {
        GameInfo current = scheduleService.getCurrentGame();
        GameInfo upcoming = scheduleService.getUpcomingGame();

        GameContext ctx;

        if (current != null) {
            ctx = new GameContext(
                    "Live game",
                    current.getSport(),
                    LocalDateTime.now(),
                    current.getOpponent(),
                    "live",
                    "Startup tweet: Arizona is playing right now"
            );
        } else if (upcoming != null) {
            ctx = new GameContext(
                    "Pregame",
                    upcoming.getSport(),
                    LocalDateTime.now(),
                    upcoming.getOpponent(),
                    "pregame",
                    "Startup tweet: Game starts soon"
            );
        } else {
            ctx = new GameContext(
                    "Daily vibes",
                    "general",
                    LocalDateTime.now(),
                    null,
                    null,
                    "Startup tweet: No game right now"
            );
        }

        TweetCategory category = TweetCategoryDecider.decide(ctx);
        String tweet = aiContentService.generate(category, ctx);
        postingService.sendTweet(tweet);
    }


    @Scheduled(cron = "${scheduler.tweetCron}")
    public void scheduleTweet() {

        int jitterSeconds = ThreadLocalRandom.current().nextInt(-600, 600);
        try { Thread.sleep(Math.max(0, jitterSeconds * 1000L)); } catch (InterruptedException ignored) {}

        GameInfo current = scheduleService.getCurrentGame();
        GameInfo upcoming = scheduleService.getUpcomingGame();

        GameContext ctx;

        if (current != null) {
            var score = scoreScraper.fetchLiveScore(current.getOpponent(), current.getSport());

            ctx = new GameContext(
                    "Live game",
                    current.getSport(),
                    LocalDateTime.now(),
                    current.getOpponent(),
                    "live",
                    score != null ? score.toString() : "no score available"
            );

        } else if (upcoming != null) {
            ctx = new GameContext(
                    "Pregame",
                    upcoming.getSport(),
                    LocalDateTime.now(),
                    upcoming.getOpponent(),
                    "pregame",
                    ""
            );

        } else {
            ctx = new GameContext(
                    "Daily vibes",
                    "general",
                    LocalDateTime.now(),
                    null,
                    null,
                    ""
            );
        }

        TweetCategory category = TweetCategoryDecider.decide(ctx);
        String tweet = aiContentService.generate(category, ctx);
        postingService.sendTweet(tweet);
    }
}
