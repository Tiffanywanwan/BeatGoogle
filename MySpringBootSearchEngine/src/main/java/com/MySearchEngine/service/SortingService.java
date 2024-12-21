package com.MySearchEngine.service;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.springframework.stereotype.Service;

import com.MySearchEngine.model.WebPageNode;

@Service
public class SortingService {
    public List<WebPageNode> sort(WebPageNode root) {
        List<WebPageNode> allNodes = flatten(root);
        PriorityQueue<WebPageNode> pq = new PriorityQueue<>((a,b)->Double.compare(b.getScore(),a.getScore()));
        pq.addAll(allNodes);

        List<WebPageNode> sorted = new ArrayList<>();
        while(!pq.isEmpty()) {
            sorted.add(pq.poll());
        }
        return sorted;
    }

    private List<WebPageNode> flatten(WebPageNode node) {
        List<WebPageNode> list = new ArrayList<>();
        if(node == null) return list;
        list.add(node);
        for(WebPageNode c : node.getChildren()) {
            list.addAll(flatten(c));
        }
        return list;
    }
}