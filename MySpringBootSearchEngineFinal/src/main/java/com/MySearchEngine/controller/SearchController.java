package com.MySearchEngine.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.MySearchEngine.model.WebPageNode;
import com.MySearchEngine.service.GoogleQueryService;
import com.MySearchEngine.service.KeywordExtractorService;
import com.MySearchEngine.service.KeywordFrequencyService; // 新增引用
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

    @Autowired
    private KeywordFrequencyService keywordFrequencyService; // 新增依賴注入

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/search")
    public String search(String q, Model model) {
        if (q == null || q.trim().isEmpty()) {
            return "redirect:/";
        }

        List<WebPageNode> rawResults = webScrapingService.scrapeWeb(q);
        if (rawResults.isEmpty()) {
            rawResults = googleQueryService.search(q);
        }

        for (WebPageNode node : rawResults) {
            double score = keywordExtractor.calculateScore(node, q);
            node.setScore(score);
        }

        if (!rawResults.isEmpty()) {
            WebPageNode root = rawResults.get(0);
            for (int i = 1; i < rawResults.size(); i++) {
                root.getChildren().add(rawResults.get(i));
            }
            scoreCalculator.postOrderCalculate(root);
            List<WebPageNode> sorted = sortingService.sort(root);
            model.addAttribute("results", sorted);

            // 提取前三個網頁的所有文字
            List<String> topThreeTexts = new ArrayList<>();
            for (int i = 0; i < Math.min(5, sorted.size()); i++) {
                String text = webScrapingService.scrapeContent(sorted.get(i).getUrl());
                topThreeTexts.add(text);
            }

            // 計算最高頻關鍵字
            List<String> topKeywords = keywordFrequencyService.extractTopKeywords(topThreeTexts, 5);
            model.addAttribute("topKeywords", topKeywords);
        } else {
            model.addAttribute("results", null);
        }

        model.addAttribute("keyword", q);
        return "result";
    }
}