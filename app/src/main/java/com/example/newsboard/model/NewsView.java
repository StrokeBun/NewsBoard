package com.example.newsboard.model;

import org.jetbrains.annotations.NotNull;

/**
 * @Title: NewsView
 * @Package: model
 * @Description: NewsView Model
 * @author: Susongfeng
 * @date: 2020/11/16 18:51
 */
public class NewsView {

    private News news;
    private String publishMessage;
    private int type;
    private int cover;

    public NewsView(News news, int type, int cover) {
        this.news = news;
        this.type = type;
        this.cover = cover;
        this.publishMessage = news.getAuthor() + "       " + news.getPublishTime();
    }

    public NewsView(News news, int type) {  // type 0
        this(news, type, 0);
    }

    @Override
    public String toString() {
        return "News{" +
                news.toString() +
                ", publishMessage='" + publishMessage + '\'' +
                ", type=" + type +
                ", cover=" + cover +
                '}';
    }

    public int getType() {
        return type;
    }

    public int getCover() {
        return cover;
    }

    public String getPublishMessage() {
        return publishMessage;
    }

    public News getNews() {
        return news;
    }
}
