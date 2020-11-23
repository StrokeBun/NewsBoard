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
import com.example.newsboard.ui.adapter.HistoryNewsAdapter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryActivity extends BaseActivity {

    private static final int HISTORY_NEWS_MAX_NUM = 5;
    // 使用LinkedHashMap实现历史记录(类似LRU)
    private static LinkedHashMap<News, Integer> historyNews;
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
        recyclerView = (RecyclerView) findViewById(R.id.history_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        newsViewList = historyNews.keySet()
                .parallelStream()
                .map(news -> new NewsView(news))
                .collect(Collectors.toList());
        Collections.reverse(newsViewList);
        HistoryNewsAdapter adapter = new HistoryNewsAdapter(newsViewList);
        recyclerView.setAdapter(adapter);
    }

    public void historyNewsClick(View view){
        int position = recyclerView.getChildAdapterPosition(view);
        NewsView newsView = newsViewList.get(position);
        News news = newsView.getNews();
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra(News.EXTRA_NEWS, news);
        startActivity(intent);
    }

    public static void addHistoryNews(News news) {
        historyNews.put(news, historyNews.size());
    }

    public static void clearHistoryNews() {
        historyNews.clear();
    }
}