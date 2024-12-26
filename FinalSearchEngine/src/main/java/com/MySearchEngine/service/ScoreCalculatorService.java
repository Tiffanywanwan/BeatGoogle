package com.MySearchEngine.service;

import org.springframework.stereotype.Service;

import com.MySearchEngine.model.WebPageNode;

@Service
public class ScoreCalculatorService {
    public double postOrderCalculate(WebPageNode node) {
        if (node == null) return 0.0;
        double sum = node.getScore();
        for (WebPageNode child : node.getChildren()) {
            sum += postOrderCalculate(child);
        }
        node.setScore(sum);
        return sum;
    }
}