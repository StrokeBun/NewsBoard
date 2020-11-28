package com.example.newsboard.ui.adapter.news;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.newsboard.R;
import com.example.newsboard.model.NewsView;

/**
 * <pre>
 *     author : Su Songfeng
 *     e-mail : 1986553865@qq.com
 *     time   : 2020/11/21 11:26
 *     desc   : 新闻排版基类
 *     version: 1.0
 * </pre>
 */
class BaseType extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements Loadable {
    /**
     * 新闻标题
     */
    TextView titleText;
    /**
     * 发布信息
     */
    TextView publishText;

    public BaseType(@NonNull View itemView) {
        super(itemView);
        titleText = itemView.findViewById(R.id.news_text);
        publishText = itemView.findViewById(R.id.publish_message);
    }

    /**
     * 加载资源
     * @param newsView 新闻排版数据
     */
    @Override
    public void load(NewsView newsView) {
        titleText.setText(newsView.getNews().getTitle());
        publishText.setText(newsView.getPublishMessage());
    }
}