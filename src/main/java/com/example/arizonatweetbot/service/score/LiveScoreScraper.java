package com.example.arizonatweetbot.service.score;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class LiveScoreScraper {

    public LiveScoreInfo fetchLiveScore(String opponent, String sport) {
        try {
            String url = buildUrl(opponent, sport);
            Document doc = Jsoup.connect(url).get();

            // Score elements
            Element teamScore = doc.selectFirst(".ScoreCell__Score");
            Element oppScore = doc.select(".ScoreCell__Score").get(1);

            // Game status (e.g., "1st Half", "Final", "3rd Quarter")
            Element status = doc.selectFirst(".Gamestrip__Status");

            if (teamScore == null || oppScore == null || status == null) {
                return null;
            }

            return new LiveScoreInfo(
                    Integer.parseInt(teamScore.text()),
                    Integer.parseInt(oppScore.text()),
                    status.text()
            );

        } catch (Exception e) {
            return null;
        }
    }

    private String buildUrl(String opponent, String sport) {
        opponent = opponent.toLowerCase().replace(" ", "-");

        if (sport.equalsIgnoreCase("basketball")) {
            return "https://www.espn.com/mens-college-basketball/game/_/id/12-vs-" + opponent;
        }

        if (sport.equalsIgnoreCase("football")) {
            return "https://www.espn.com/college-football/game/_/id/12-vs-" + opponent;
        }

        return "";
    }
}
