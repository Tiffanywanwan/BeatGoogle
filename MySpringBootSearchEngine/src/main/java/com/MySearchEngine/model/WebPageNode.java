package com.MySearchEngine.model;

import java.util.ArrayList;
import java.util.List;

public class WebPageNode {
    private String name;
    private String url;
    private double score;
    private String imageUrl;
    private List<WebPageNode> children = new ArrayList<>();

    public WebPageNode(String name, String url, double score) {
        this(name, url, score, null);
    }

    public WebPageNode(String name, String url, double score, String imageUrl) {
        this.name = name;
        this.url = url;
        this.score = score;
        this.imageUrl = imageUrl;
    }
    // Getter / Setter 省略
    public String getName() { return name; }
    public String getUrl() { return url; }
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public List<WebPageNode> getChildren() { return children; }
}