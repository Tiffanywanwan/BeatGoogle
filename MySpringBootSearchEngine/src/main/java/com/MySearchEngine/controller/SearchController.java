package com.MySearchEngine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.MySearchEngine.model.WebPageNode;
import com.MySearchEngine.service.GoogleQueryService;
import com.MySearchEngine.service.KeywordExtractorService;
import com.MySearchEngine.service.ScoreCalculatorService;
import com.MySearchEngine.service.SortingService;
import com.MySearchEngine.service.WebScrapingService;

@Controller
public class SearchController {

    @Autowired
    private GoogleQueryService googleQueryService;

    @Autowired
    private KeywordExtractorService keywordExtractor;

    @Autowired
    private ScoreCalculatorService scoreCalculator;

    @Autowired
    private SortingService sortingService;

    @Autowired
    private WebScrapingService webScrapingService;

    // 回傳首頁
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // 搜尋結果 (表單送至 /search)
    @GetMapping("/search")
    public String search(String q, Model model) {
        // 0. 若無輸入 -> 回首頁
        if (q == null || q.trim().isEmpty()) {
            return "redirect:/";
        }

        // 1. 使用 WebScrapingService 進行真實爬取
        List<WebPageNode> rawResults = webScrapingService.scrapeWeb(q);

        // 2. 若爬取的結果為空，回退到 GoogleQueryService 提供假資料
        if (rawResults.isEmpty()) {
            rawResults = googleQueryService.search(q);
        }

        // 3. 計算關鍵字分數，傳遞查詢關鍵字
        for (WebPageNode node : rawResults) {
            double score = keywordExtractor.calculateScore(node, q);
            node.setScore(score);
        }

        // 4. 若需要建樹狀結構
        if (!rawResults.isEmpty()) {
            WebPageNode root = rawResults.get(0);
            for (int i = 1; i < rawResults.size(); i++) {
                root.getChildren().add(rawResults.get(i));
            }
            // 5. 後序遍歷加總
            scoreCalculator.postOrderCalculate(root);
            // 6. MaxHeap 排序
            List<WebPageNode> sorted = sortingService.sort(root);
            model.addAttribute("results", sorted);
        } else {
            model.addAttribute("results", null);
        }

        model.addAttribute("keyword", q);
        return "result";
    }
}