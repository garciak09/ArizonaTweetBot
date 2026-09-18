package com.example.arizonatweetbot.service.scheduler;

import com.example.arizonatweetbot.domain.GameInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleScraper {

    private static final String BASKETBALL_URL =
            "https://www.espn.com/mens-college-basketball/team/schedule/_/id/12";
    private static final String FOOTBALL_URL =
            "https://www.espn.com/college-football/team/schedule/_/id/12";

    public List<GameInfo> fetchSchedule() {
        List<GameInfo> games = new ArrayList<>();
        games.addAll(scrapeSport("basketball", BASKETBALL_URL));
        games.addAll(scrapeSport("football", FOOTBALL_URL));
        return games;
    }

    private List<GameInfo> scrapeSport(String sport, String url) {
        List<GameInfo> list = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).get();
            Elements rows = doc.select("table tbody tr");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a");

            for (Element row : rows) {
                Elements cols = row.select("td");

                // Skip header or malformed rows
                if (cols.size() < 3) continue;

                String dateText = cols.get(0).text().trim();
                String opponent = cols.get(1).text().trim();
                String timeText = cols.get(2).text().trim();

                // Skip header rows like "DATE RESULT"
                if (dateText.equalsIgnoreCase("DATE") || opponent.equalsIgnoreCase("OPPONENT")) {
                    continue;
                }

                // Skip rows with TBD or no time
                if (timeText.equalsIgnoreCase("TBD") ||
                        timeText.equalsIgnoreCase("Postponed") ||
                        timeText.equalsIgnoreCase("Canceled") ||
                        timeText.isBlank()) {
                    continue;
                }

                // ESPN sometimes uses "W 82-71" or "L 65-70" instead of a time
                if (timeText.matches("^[WL]\\s.*")) {
                    continue;
                }

                // Combine date + time
                String dateTime = dateText + " " + timeText;

                LocalDateTime start;
                try {
                    start = LocalDateTime.parse(dateTime, formatter);
                } catch (Exception e) {
                    // Skip rows that don't match expected format
                    continue;
                }

                list.add(new GameInfo(sport, opponent, start));
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to scrape ESPN schedule", e);
        }

        return list;
    }

}
