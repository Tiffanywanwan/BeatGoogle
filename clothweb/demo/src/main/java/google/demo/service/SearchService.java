package google.demo.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import google.util.HTMLHandler;
import google.util.ScoreCalculator;

@Service
public class SearchService {

    private final HTMLHandler htmlHandler;
    private final ScoreCalculator scoreCalculator;

    public SearchService(HTMLHandler htmlHandler, ScoreCalculator scoreCalculator) {
        this.htmlHandler = htmlHandler;
        this.scoreCalculator = scoreCalculator;
    }

    public Map<String, String> search(String query) {
        Map<String, String> results = new HashMap<>();
        try {
            // 加上額外關鍵字並進行 URL 編碼
            String modifiedQuery = query + " clothes fashion";
            String encodedQuery = URLEncoder.encode(modifiedQuery, "UTF-8");

            // 抓取內容
            String content = htmlHandler.fetchContent("https://www.google.com/search?q=" + encodedQuery + "&oe=utf8&num=20");
            Document doc = Jsoup.parse(content);

            // 分析搜尋結果
            Elements elements = doc.select("div.kCrYT");
            for (Element element : elements) {
                String title = element.select("a").text();
                String url = element.select("a").attr("href").replace("/url?q=", "").split("&")[0];

                if (!title.isEmpty()) {
                    double score = scoreCalculator.calculateScore(title, query);
                    results.put(title + " (Score: " + score + ")", url);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }
}
