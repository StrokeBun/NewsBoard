package com.example.newsboard.model;

/**
 * @Title: News
 * @Package: model
 * @Description: News Model
 * @author: Susongfeng
 * @date: 2020/11/16 18:51
 */
public class News {
    private String id;
    private String title;
    private String author;
    private String publishTime;
    private String publishMessage;
    private int type;
    private int cover;


    public News(String id, String title, int type, int cover, String author, String publishTime) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.cover = cover;
        this.author = author;
        this.publishTime = publishTime;
        this.publishMessage = this.author + "       " + this.publishTime;
    }


    public News(String id, String title, int type, String author, String publishTime) {  // type 0
        this(id, title, type, 0, author, publishTime);
    }

    public int getType() {
        return type;
    }

    public int getCover() {
        return cover;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishMessage() {
        return publishMessage;
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
