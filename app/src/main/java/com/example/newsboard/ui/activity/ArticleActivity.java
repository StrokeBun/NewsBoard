package com.example.newsboard.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.newsboard.R;
import com.example.newsboard.base.BaseActivity;
import com.example.newsboard.util.HttpUtils;
import com.example.newsboard.util.TokenUtils;
import com.zzhoujay.richtext.RichText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class ArticleActivity extends BaseActivity {

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_AUTHOR = "author";
    public static final String EXTRA_PUBlISH_TIME = "publishTime";

    private static final String ARTICLE_URL = "https://vcapi.lvdaqian.cn/article/";
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
        }
        initArticle();
    }

    private void init()  {
        setContentView(R.layout.activity_article);
        titleTextView = findViewById(R.id.article_title);
        authorTextView = findViewById(R.id.article_author);
        punishedTimeTextView = findViewById(R.id.publish_time);
        contentTextView = findViewById(R.id.article_content);
    }

    private void initArticle() {
        Intent intent = getIntent();
        setTitle(intent.getStringExtra(EXTRA_TITLE));
        setAuthor(intent.getStringExtra(EXTRA_AUTHOR));
        setPunishedTime(intent.getStringExtra(EXTRA_PUBlISH_TIME));
        String id = intent.getStringExtra(EXTRA_ID);
        setContent(id, true);
    }

    private void setTitle(String title) {
        titleTextView.setText(title);
    }

    private void setAuthor(String author) {
        authorTextView.setText(author);
    }

    private void setPunishedTime(String punishedTime) {
        punishedTimeTextView.setText(punishedTime);
    }

    private void setContent(String content) {
        contentTextView.setText(content);
    }

    private void setContent(String id, boolean isMarkdownFormat) {
        new Thread(() -> {
            String url = ARTICLE_URL + id;
            url = isMarkdownFormat? url+MARKDOWN_ARTICLE_URL_SUFFIX:url;
            try {
                Map<String, String> header = TokenUtils.getAuthorizationHeader();
                String response = HttpUtils.get(url, header);
                if (response == null) {
                    return;
                }
                JSONObject jsonObject = new JSONObject(response);
                String content = (String) jsonObject.get("data");
                loadArticle(content, isMarkdownFormat);
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }).start();
    }

    private void loadArticle(String content, boolean isMarkdownFormat) {
        // 只有主线程能渲染TextView
        if (isMarkdownFormat) {
            ArticleActivity.this.runOnUiThread(() -> RichText.fromMarkdown(content).into(contentTextView));
        }
        else {
            ArticleActivity.this.runOnUiThread(() -> setContent(content));
        }
    }

}