package com.MySearchEngine.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.MySearchEngine.model.WebPageNode;

@Service
public class GoogleQueryService {
    private static final String[] EXTRA_KEYWORDS = {"服飾", "購買"};

    public List<WebPageNode> search(String query) {
        // 自動組合 + 假資料
        StringBuilder combined = new StringBuilder(query.trim());
            for (String kw : EXTRA_KEYWORDS) {
                combined.append(" ").append(kw);
            }
            List<WebPageNode> results = new ArrayList<>();
        
            WebPageNode node1 = new WebPageNode("【" + query + "】服飾購買頁", 
                                                "https://example.com/" + query + "-clothes", 0.0);
            node1.setImageUrl("https://example.com/images/" + query + "_clothes.jpg");
            results.add(node1);
        
            WebPageNode node2 = new WebPageNode(query + "潮流服裝介紹", 
                                                "https://blog.example.com/" + query + "-fashion", 0.0);
            node2.setImageUrl("https://example.com/images/" + query + "_fashion.jpg");
            results.add(node2);
        
            WebPageNode node3 = new WebPageNode(query + "圖案T恤", 
                                                "https://shop.example.com/" + query + "-tshirt", 0.0);
            node3.setImageUrl("https://example.com/images/" + query + "_tshirt.jpg");
            results.add(node3);
        
            return results;
        }
        
    }
