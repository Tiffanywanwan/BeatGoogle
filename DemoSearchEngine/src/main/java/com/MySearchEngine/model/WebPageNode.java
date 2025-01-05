package com.MySearchEngine.model;

import java.util.ArrayList;
import java.util.List;

public class WebPageNode {
    private String name;
    private String url;
    private double score;
    private String imageUrl;
    private String title;         // Added field for title
    private String description;   // Added field for description
    private String content;       // Added field for content
    private List<WebPageNode> children = new ArrayList<>();

    // Constructor with basic fields
    public WebPageNode(String name, String url, double score) {
        this(name, url, score, null, null, null, null);
    }

    // Constructor with all fields
    public WebPageNode(String name, String url, double score, String imageUrl, String title, String description, String content) {
        this.name = name;
        this.url = url;
        this.score = score;
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.content = content;
    }

    // New constructor with just the 4 fields (used in GoogleQueryService)
    public WebPageNode(String name, String url, double score, String imageUrl) {
        this(name, url, score, imageUrl, null, null, null);  // Default values for title, description, and content
    }

    // Getter/Setter methods
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public List<WebPageNode> getChildren() { return children; }
    public void setChildren(List<WebPageNode> children) { this.children = children; }
}
