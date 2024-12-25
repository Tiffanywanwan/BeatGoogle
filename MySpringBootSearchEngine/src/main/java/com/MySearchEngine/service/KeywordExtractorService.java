package com.MySearchEngine.service;

import org.springframework.stereotype.Service;

import com.MySearchEngine.model.WebPageNode;

@Service
public class KeywordExtractorService {

    private static final String[] KEYWORDS = {"服裝", "服飾", "衣服", "購買", "上衣", "褲子", "裙子", "配件", "折扣", "新品"};
    private static final double[] WEIGHTS = {2.0, 2.0, 1.5, 2.5, 1.0, 1.0, 1.0, 1.0, 1.5, 2.0};

    public double calculateScore(WebPageNode node, String query) {
        String content = node.getName().toLowerCase();
        double score = 0.0;

        // 計算靜態關鍵字的分數
        for (int i = 0; i < KEYWORDS.length; i++) {
            int count = countOccurrences(content, KEYWORDS[i]);
            score += count * WEIGHTS[i];
        }

        // 計算查詢關鍵字的分數
        if (query != null && !query.trim().isEmpty()) {
            int queryCount = countOccurrences(content, query.toLowerCase());
            // 為查詢關鍵字指定一個權重，例如 3.0
            score += queryCount * 0.25;
        }

        return score;
    }

    private int countOccurrences(String text, String pattern) {
        if (pattern == null || pattern.isEmpty()) return 0;
        int count = 0;
        int index = 0;
        pattern = pattern.toLowerCase();
        while ((index = text.indexOf(pattern, index)) != -1) {
            count++;
            index += pattern.length();
        }
        return count;
    }
}