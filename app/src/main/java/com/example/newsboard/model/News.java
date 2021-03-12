package com.example.newsboard.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

/**
 * <pre>
 *     author : Su Songfeng
 *     e-mail : 1986553865@qq.com
 *     time   : 2020/11/15 20:57
 *     desc   : News information，实现Serializable接口便于在Intent中传递
 *     version: 1.0
 * </pre>
 */

/**
 * <pre>
 *     author : Zhong Defeng
 *     e-mail : 1756809298@qq.com
 *     time   : 2021/3/11 15:58
 *     desc   : 使用 Parcelable 接口代替
 *     version: 2.0
 * </pre>
 */
public class News implements Parcelable {

    /**
     * 在Activity间传递新闻Intent所使用的key
     */
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

    public News(Parcel in) {
        id = in.readString();
        title = in.readString();
        author = in.readString();
        publishTime = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(publishTime);
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

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
