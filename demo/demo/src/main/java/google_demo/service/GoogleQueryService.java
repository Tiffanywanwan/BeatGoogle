package google_demo.service;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class GoogleQueryService {
    public HashMap<String, String> search(String keyword) throws IOException {
        String url = "https://www.google.com/search?q=" + java.net.URLEncoder.encode(keyword, "UTF-8");
        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
        Elements results = doc.select("div.kCrYT");

        HashMap<String, String> searchResults = new HashMap<>();
        for (Element result : results) {
            try {
                String title = result.select("a > h3").text();
                String link = result.select("a").attr("href").replace("/url?q=", "");
                if (!title.isEmpty() && !link.isEmpty()) {
                    searchResults.put(title, link);
                }
            } catch (Exception ignored) {
            }
        }
        return searchResults;
    }
}
