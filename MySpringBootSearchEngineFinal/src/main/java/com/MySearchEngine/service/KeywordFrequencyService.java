package com.MySearchEngine.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class KeywordFrequencyService {

    // 新增正則表達式過濾條件，只匹配中文
    private static final Pattern CHINESE_PATTERN = Pattern.compile("[\u4e00-\u9fa5]+");

    public List<String> extractTopKeywords(List<String> texts, int topN) {
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String text : texts) {
            // 使用正則表達式提取中文詞語
            Matcher matcher = CHINESE_PATTERN.matcher(text);
            while (matcher.find()) {
                String word = matcher.group(); // 匹配到的中文詞語
                frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
            }
        }

        // 使用優先佇列按頻率排序
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
            (a, b) -> b.getValue().compareTo(a.getValue())
        );
        pq.addAll(frequencyMap.entrySet());

        List<String> topKeywords = new ArrayList<>();
        for (int i = 0; i < topN && !pq.isEmpty(); i++) {
            topKeywords.add(pq.poll().getKey());
        }

        return topKeywords;
    }
}