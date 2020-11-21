package com.example.newsboard.model;

import java.io.Serializable;

/**
 * <pre>
 *     author : zdf
 *     time   : 2020/11/21
 * </pre>
 */
public class News implements Serializable {

    public static final String EXTRA_NEWS = "news";

    private String id;
    private String title;
    private String author;
    private String publishTime;

    public News(String id, String title, String author, String publishTime) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishTime = publishTime;
    }

    @Override
    public String toString() {
        return "News{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishTime='" + publishTime + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public String getId() {
        return id;
    }
}
