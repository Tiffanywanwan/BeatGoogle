package com.MySearchEngine.controller;

import com.MySearchEngine.model.WebPageNode;
import com.MySearchEngine.service.GoogleQueryService;
import com.MySearchEngine.service.KeywordExtractorService;
import com.MySearchEngine.service.ScoreCalculatorService;
import com.MySearchEngine.service.SortingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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

    // 回傳首頁
    @GetMapping("/")
    public String home() {
        return "index"; 
    }

    // 搜尋結果 (表單送至 /search)
    @GetMapping("/search")
    public String search(String q, Model model) {
        // 0. 若無輸入 -> 回首頁
        if(q == null || q.trim().isEmpty()) {
            return "redirect:/";
        }
        
        // 1. 摳 Google (假資料)
        List<WebPageNode> rawResults = googleQueryService.search(q);

        // 2. 計算關鍵字分數
        for(WebPageNode node : rawResults) {
            double score = keywordExtractor.calculateScore(node);
            node.setScore(score);
        }

        // 3. 若需要建樹狀結構
        if(!rawResults.isEmpty()) {
            WebPageNode root = rawResults.get(0);
            for(int i=1; i<rawResults.size(); i++){
                root.getChildren().add(rawResults.get(i));
            }
            // 4. 後序遍歷加總
            scoreCalculator.postOrderCalculate(root);
            // 5. MaxHeap 排序
            List<WebPageNode> sorted = sortingService.sort(root);
            model.addAttribute("results", sorted);
        } else {
            model.addAttribute("results", null);
        }

        model.addAttribute("keyword", q);
        return "result"; 
    }
}