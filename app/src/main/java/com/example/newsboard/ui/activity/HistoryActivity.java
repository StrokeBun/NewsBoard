package com.example.newsboard.ui.activity;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.newsboard.R;
import com.example.newsboard.base.BaseActivity;
import com.example.newsboard.model.News;
import com.example.newsboard.model.NewsView;
import com.example.newsboard.ui.adapter.news.NewsAdapter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 *     author : Zhong DeFeng
 *     e-mail : 1756809298@qq.com
 *     time   : 2020/11/24 20:57
 *     desc   : 浏览历史页面活动
 *     version: 1.0
 * </pre>
 */
public class HistoryActivity extends BaseActivity {
    // 浏览历史存储最大数量
    private static final int HISTORY_NEWS_MAX_NUM = 5;
    // 使用LinkedHashMap存储历史记录(类似LRU)
    private static final LinkedHashMap<News, Integer> historyNews;
    private List<NewsView> newsViewList;
    static {
        historyNews = new LinkedHashMap<News, Integer>(HISTORY_NEWS_MAX_NUM, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Entry<News, Integer> eldest) {
                return historyNews.size() > HISTORY_NEWS_MAX_NUM;
            }
        };
    }
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recyclerView = findViewById(R.id.history_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        newsViewList = historyNews.keySet()
                .parallelStream()
                .map(NewsView::new)
                .collect(Collectors.toList());
        Collections.reverse(newsViewList);
        NewsAdapter adapter = new NewsAdapter(newsViewList);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 浏览历史的点击处理方法，跳转到相应文章页面
     * @param view View
     */
    public void historyNewsClick(View view){
        int position = recyclerView.getChildAdapterPosition(view);
        NewsView newsView = newsViewList.get(position);
        News news = newsView.getNews();
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra(News.EXTRA_NEWS, news);
        startActivity(intent);
    }

    /**
     * 在浏览历史上添加新闻
     * @param news 新闻
     */
    public static void addHistoryNews(News news) {
        historyNews.put(news, historyNews.size());
    }

    /**
     * 清除浏览历史
     */
    public static void clearHistoryNews() {
        historyNews.clear();
    }
}