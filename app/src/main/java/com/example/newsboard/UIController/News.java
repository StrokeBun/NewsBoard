package com.example.newsboard.UIController;

/**
 * @Title: News
 * @Package: UIController
 * @Description: News Model
 * @author: Susongfeng
 * @date: 2020/11/16 18:51
 */
class News {
    private String title;
    private String author;
    private String publishTime;
    private String publishMessage;
    private int type;
    private int cover;


    public News(String title, int type, int cover, String author, String publishTime) {
        this.title = title;
        this.type = type;
        this.cover = cover;
        this.author = author;
        this.publishTime = publishTime;
        this.publishMessage = this.author + "       " + this.publishTime;
    }

    public News(String title, int type, String author, String publishTime) {  // type 0
        this.title = title;
        this.type = type;
        this.cover = 0;
        this.author = author;
        this.publishTime = publishTime;
        this.publishMessage = this.author + "       " + this.publishTime;
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
}
