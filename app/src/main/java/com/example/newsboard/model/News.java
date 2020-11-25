package com.example.newsboard.model;

import androidx.annotation.Nullable;

import java.io.Serializable;

/**
 * <pre>
 *     author : Su Songfeng
 *     e-mail : 1986553865@qq.com
 *     time   : 2020/11/15 20:57
 *     desc   : News information
 *     version: 1.0
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof News) {
            return this.id.equals( ((News) obj).getId())
                    && this.author.equals(((News) obj).getAuthor())
                    && this.title.equals(((News) obj).getTitle())
                    && this.publishTime.equals(((News) obj).publishTime);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = id.hashCode();
        hash = hash*31  + author.hashCode();
        hash = hash*31 + title.hashCode();
        hash = hash*31 + publishTime.hashCode();
        return hash;
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
