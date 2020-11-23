package com.example.newsboard.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.newsboard.R;
import com.example.newsboard.base.BaseActivity;
import com.example.newsboard.model.News;
import com.example.newsboard.util.HttpUtils;
import com.example.newsboard.util.StringUtils;
import com.example.newsboard.util.TokenUtils;

import com.zzhoujay.glideimagegetter.GlideImageGetter;
import com.zzhoujay.richtext.RichText;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class ArticleActivity extends BaseActivity {

    // 请求文章的http url
    private static final String ARTICLE_URL = "https://vcapi.lvdaqian.cn/article/";
    // 请求markdown格式文章的http url后缀
    private static final String MARKDOWN_ARTICLE_URL_SUFFIX = "?markdown=true";

    private TextView titleTextView = null;
    private TextView authorTextView = null;
    private TextView punishedTimeTextView = null;
    private TextView contentTextView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (TokenUtils.isEmptyToken()) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            Intent intent = getIntent();
            News news = (News) intent.getSerializableExtra(News.EXTRA_NEWS);
            initArticle(news);
            // 在浏览历史中添加此文章
            HistoryActivity.addHistoryNews(news);
        }
    }

    private void init()  {
        setContentView(R.layout.activity_article);
        titleTextView = findViewById(R.id.article_title);
        authorTextView = findViewById(R.id.article_author);
        punishedTimeTextView = findViewById(R.id.publish_time);
        contentTextView = findViewById(R.id.article_content);
    }

    private void initArticle(News news) {
        setTitle(news.getTitle());
        setAuthor(news.getAuthor());
        setPunishedTime(news.getPublishTime());
        setContent(news.getId(), true);
    }

    private void setContent(String content) {
        contentTextView.setText(content);
    }

    private void setContent(String id, boolean isMarkdownFormat) {
        new Thread(() -> {
            String url = ARTICLE_URL + id;
            url = isMarkdownFormat? url+MARKDOWN_ARTICLE_URL_SUFFIX:url;
            Map<String, String> header = TokenUtils.getAuthorizationHeader();
            String response = HttpUtils.get(url, header);
            if (response == null) {
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(response);
                String content = (String) jsonObject.get("data");
                content = StringUtils.handleMarkdown(content);
                loadArticle(content, isMarkdownFormat);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void loadArticle(String content, boolean isMarkdownFormat) {
        // 在UI线程渲染文本
        if (isMarkdownFormat) {
            ArticleActivity.this.runOnUiThread(() -> RichText.fromMarkdown(content).into(contentTextView));
            Log.e("ok", "ok");
        } else {
            ArticleActivity.this.runOnUiThread(() -> setContent(content));
        }
    }

    private void setTitle(String title) {
        titleTextView.setText(title);
    }

    private void setAuthor(String author) {
        authorTextView.setText(author);
    }

    private void setPunishedTime(String punishedTime) { punishedTimeTextView.setText(punishedTime); }

}