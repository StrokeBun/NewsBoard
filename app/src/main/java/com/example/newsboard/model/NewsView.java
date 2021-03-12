package com.example.newsboard.model;

/**
 * <pre>
 *     author : Su Songfeng
 *     e-mail : 1986553865@qq.com
 *     time   : 2020/11/16 18:51
 *     desc   : NewsView Model
 *     version: 1.0
 * </pre>
 */
public class NewsView {

    private News news;
    private String publishMessage;
    private int type;
    private int cover;

    private static final int DEFAULT_TYPE = 5;

    public NewsView(News news, int type, int cover) {
        this.news = news;
        this.type = type;
        this.cover = cover;
        this.publishMessage = news.getAuthor() + "       " + news.getPublishTime();
    }

    public NewsView(News news, int type) {  // type 0
        this(news, type, 0);
    }

    public NewsView(News news) {
        this(news, DEFAULT_TYPE, 0);
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

    public News getNews() {
        return news;
    }

    public String getPublishMessage() {
        return publishMessage;
    }

    public int getType() {
        return type;
    }

    public int getCover() {
        return cover;
    }

}
