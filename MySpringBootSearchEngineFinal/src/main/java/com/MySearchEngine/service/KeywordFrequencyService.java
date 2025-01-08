package com.MySearchEngine.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class KeywordFrequencyService {

    // 正則表達式過濾條件，只匹配中文
    private static final Pattern CHINESE_PATTERN = Pattern.compile("[\u4e00-\u9fa5]+");

    // 停用詞列表
    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
        "的", "是", "在", "了", "和", "及", "與", "也", "或", "但","露天市集","月更新","阿里巴巴","台灣"
    ));

    public List<String> extractTopKeywords(List<String> texts, int topN) {
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String text : texts) {
            // 使用正則表達式提取中文詞語
            Matcher matcher = CHINESE_PATTERN.matcher(text);
            while (matcher.find()) {
                String word = matcher.group(); // 匹配到的中文詞語
                // 過濾停用詞並限制詞語長度為兩到四個字
                if (word.length() >= 2 && word.length() <= 4 && !STOP_WORDS.contains(word)) {
                    frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
                }
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