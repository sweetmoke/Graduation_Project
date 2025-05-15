package com.example.springboot.entity;

public class Information {
    private Integer newsId;
    private String title;
    private String content;
    private String link;
    private Boolean isRead;
    private String createdAt;

    public Information() {}

    public Information(Integer newsId, String title, String content, String link, Boolean isRead, String createdAt) {
        this.newsId = newsId;
        this.title = title;
        this.content = content;
        this.link = link;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
