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

    private static final String[] EXTRA_KEYWORDS = {"服裝", "購買"};

    public List<WebPageNode> scrapeWeb(String query) {
        List<WebPageNode> results = new ArrayList<>();
        try {
            String modifiedQuery = query + " " + String.join(" ", EXTRA_KEYWORDS);
            String url = "https://www.google.com/search?q=" + modifiedQuery + "&num=50";

            Document doc = Jsoup.connect(url)
                                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                                .get();

            int maxResults = 15;
            Elements searchResults = doc.select("div.g");
            for (Element result : searchResults) {
                if (results.size() >= maxResults) break;

                Element link = result.selectFirst("a");
                Element title = result.selectFirst("h3");

                if (link != null && title != null) {
                    String resultUrl = link.attr("href");
                    String resultTitle = title.text();
                    results.add(new WebPageNode(resultTitle, resultUrl, 0.0));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }

    // 新增提取網頁內容的方法
    public String scrapeContent(String url) {
        try {
            Document doc = Jsoup.connect(url)
                                .userAgent("Mozilla/5.0")
                                .get();
            return doc.title(); // 提取網頁正文文字
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}