package com.MySearchEngine.service;

import org.springframework.stereotype.Service;

import com.MySearchEngine.model.WebPageNode;

@Service
public class KeywordExtractorService {

    // 新增的關鍵字
    private static final String[] KEYWORDS = {
        "服飾","服裝", "衣服", "T恤", "購買", "上衣", "褲子", "裙子", "配件", "折扣", "新品",
        "評價", "評分", "產品", "出貨", "商品", "優惠", "購物車", "取貨"
    };

    // 新增關鍵字的權重
    private static final double[] WEIGHTS = {
        3.0, 4.5, 4.5, 5.0, 4.5, 1.0, 1.0, 1.0, 1.0, 2.5, 2.0,   // 原來的權重
        2.0, 1.8, 1.5, 1.2, 2.0, 1.5, 1.2, 1.0          // 新增的關鍵字的權重
    };

    private static final double AMAZON_BONUS = 2.5;  // Amazon 額外加分
    private static final double Ruten_BONUS = 2.2;   // 露天拍賣額外加分

    public double calculateScore(WebPageNode node) {
        String content = node.getName().toLowerCase();
        double score = 0.0;

        // 計算關鍵字匹配得分
        for (int i = 0; i < KEYWORDS.length; i++) {
            int count = countOccurrences(content, KEYWORDS[i]);
            score += count * WEIGHTS[i];
        }

        // 檢查網址來自 Amazon 或 露天拍賣，並加分
        String url = node.getUrl().toLowerCase();
        if (url.contains("amazon")) {
            score += AMAZON_BONUS;  // 如果來自 Amazon，增加額外分數
        } else if (url.contains("ruten.com.tw")) {  // 露天拍賣的域名
            score += Ruten_BONUS;  // 如果來自露天拍賣，增加額外分數
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
