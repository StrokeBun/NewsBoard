package com.example.newsboard.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsboard.R;
import com.example.newsboard.base.BaseActivity;
import com.example.newsboard.model.News;
import com.example.newsboard.util.HttpUtils;
import com.example.newsboard.util.StringUtils;
import com.example.newsboard.util.TokenUtils;

import com.zzhoujay.richtext.RichText;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * <pre>
 *     author : Zhong DeFeng
 *     e-mail : 1756809298@qq.com
 *     time   : 2020/11/16 18:05
 *     desc   : 文章详情页面活动
 *     version: 1.0
 * </pre>
 */
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
        setContentView(R.layout.activity_article);
        initComponents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (TokenUtils.isNotLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            Intent intent = getIntent();
            News news = (News) intent.getSerializableExtra(News.EXTRA_NEWS);
            loadArticle(news);
            // 在浏览历史中添加文章
            HistoryActivity.addHistoryNews(news);
        }
    }

    /**
     * 初始化组件
     */
    private void initComponents()  {
        titleTextView = findViewById(R.id.article_title);
        authorTextView = findViewById(R.id.article_author);
        punishedTimeTextView = findViewById(R.id.publish_time);
        contentTextView = findViewById(R.id.article_content);
    }

    /**
     * 加载文章信息并显示
     * @param news 新闻
     */
    private void loadArticle(News news) {
        setTitle(news.getTitle());
        setAuthor(news.getAuthor());
        setPunishedTime(news.getPublishTime());
        setContent(news.getId(), true);
    }


    /**
     * 通过网络加载文章内容
     * @param id 文章id
     * @param isMarkdownFormat true以markdown格式显示，false以普通文本显示
     */
    private void setContent(String id, boolean isMarkdownFormat) {
        new Thread(() -> {
            String url = ARTICLE_URL + id;
            url = isMarkdownFormat? url+MARKDOWN_ARTICLE_URL_SUFFIX:url;
            Map<String, String> header = TokenUtils.getAuthorizationHeader();
            String response = HttpUtils.get(url, header);
            // Token已经过期则跳转到登录页面
            if (response == HttpUtils.UNAUTHORIZED_RESPONSE) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "用户信息已过期", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                });
                return;
            }
            if (response == null) {
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(response);
                String content = (String) jsonObject.get("data");
                content = StringUtils.handleMarkdown(content);
                showContent(content, isMarkdownFormat);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 显示文章内容
     * @param content 文章内容字符串
     * @param isMarkdownFormat true以markdown格式显示，false以普通文本显示
     */
    private void showContent(String content, boolean isMarkdownFormat) {
        if (isMarkdownFormat) {
            ArticleActivity.this.runOnUiThread(() -> RichText.fromMarkdown(content).into(contentTextView));
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

    private void setContent(String content) { contentTextView.setText(content); }
}