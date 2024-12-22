package com.MySearchEngine.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.MySearchEngine.model.WebPageNode;

@Service
public class WebScrapingService {

    private static final String[] EXTRA_KEYWORDS = {"服飾", "購買", "商品"};

    public List<WebPageNode> scrapeWeb(String query) {
        List<WebPageNode> results = new ArrayList<>();
        try {
            // 組合關鍵字
            String modifiedQuery = query + " " + String.join(" ", EXTRA_KEYWORDS);
            String url = "https://www.google.com/search?q=" + modifiedQuery;

            // 使用 Jsoup 爬取資料
            Document doc = Jsoup.connect(url)
                                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                                .get();

            // 選取搜索結果容器
            Elements searchResults = doc.select("div.g");
            for (Element result : searchResults) {
                Element link = result.selectFirst("a");
                Element title = result.selectFirst("h3");

                if (link != null && title != null) {
                    String resultUrl = link.attr("href");
                    String resultTitle = title.text();
                    results.add(new WebPageNode(resultTitle, resultUrl, 0.0));
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // 日誌異常
        }

        return results;
    }
}
