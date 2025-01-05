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

    private static final String[] EXTRA_KEYWORDS = {"服飾", "衣服", "購買"};

    public List<WebPageNode> scrapeWeb(String query) {
        List<WebPageNode> results = new ArrayList<>();
        try {
            // 組合關鍵字
            String modifiedQuery = query + " " + String.join(" ", EXTRA_KEYWORDS);
            String url = "https://www.google.com/search?q=" + modifiedQuery + "圖案衣服" + "&num=50";

            // 使用 Jsoup 爬取資料
            Document doc = Jsoup.connect(url)
                                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                                .get();

            // 選取搜索結果容器
            int maxResults = 15;
            Elements searchResults = doc.select("div.g");
            for (Element result : searchResults) {
                if (results.size() >= maxResults) break; // 如果已經達到最大結果數量，停止添加

                Element link = result.selectFirst("a");
                Element title = result.selectFirst("h3");

                // 確認有標題和連結
                if (link != null && title != null) {
                    String resultUrl = link.attr("href");
                    String resultTitle = title.text();

                    // 爬取頁面的內容
                    Document pageDoc = Jsoup.connect(resultUrl).get();
                    Elements paragraphs = pageDoc.select("p");  // 提取所有的 <p> 標籤
                    StringBuilder content = new StringBuilder();
                    for (Element paragraph : paragraphs) {
                        content.append(paragraph.text()).append("\n");
                    }

                    // 新建 WebPageNode 物件，並設置內容
                    WebPageNode node = new WebPageNode(resultTitle, resultUrl, 0.0);
                    node.setContent(content.toString());  // 設置爬取的內容
                    results.add(node);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // 日誌異常
        }

        return results;
    }
}
