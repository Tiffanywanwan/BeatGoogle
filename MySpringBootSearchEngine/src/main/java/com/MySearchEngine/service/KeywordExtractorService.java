package com.MySearchEngine.service;

import org.springframework.stereotype.Service;

import com.MySearchEngine.model.WebPageNode;

@Service
public class KeywordExtractorService {

    private static final String[] KEYWORDS = {"服飾","衣服","購買","上衣","褲子","裙子","配件","折扣","新品"};
    private static final double[] WEIGHTS = {2.0,1.5,3.0,1.0,1.0,1.0,1.0,2.5,2.0};

    public double calculateScore(WebPageNode node) {
        String content = node.getName().toLowerCase();
        double score = 0.0;
        for (int i = 0; i < KEYWORDS.length; i++) {
            int count = countOccurrences(content, KEYWORDS[i]);
            score += count * WEIGHTS[i];
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