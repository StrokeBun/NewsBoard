package com.example.newsboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.newsboard.util.HttpUtil;
import com.example.newsboard.util.TokenUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class ArticleActivity extends AppCompatActivity {

    private static final String ARTICLE_URL = "https://vcapi.lvdaqian.cn/article/";
    private static final String MARKDOWN_ARTICLE_URL_SUFFIX = "?markdown=true";

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String PUBlISH_TIME = "publishTime";

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
        if (TokenUtil.hasToken()) {
            initArticle();
        } else {
            startActivity(new Intent(ArticleActivity.this, LoginActivity.class));
        }
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
        setTitle(intent.getStringExtra(TITLE));
        setAuthor(intent.getStringExtra(AUTHOR));
        setPunishedTime(intent.getStringExtra(PUBlISH_TIME));
        String id = intent.getStringExtra(ID);
        getArticle(id, false);
    }

    private void getArticle(String id, boolean isMarkdownFormat) {
        new Thread(() -> {
            String url = ARTICLE_URL;
            url += id;
            url = isMarkdownFormat? url+MARKDOWN_ARTICLE_URL_SUFFIX:url;
            HttpURLConnection connection = null;
            try {
                String auth = TokenUtil.getAuthorization();
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", auth);
                String response = HttpUtil.get(url, header);
                JSONObject jsonObject = new JSONObject(response);
                String content = (String) jsonObject.get("data");
                // 通过runOnUiThread显示文章
                ArticleActivity.this.runOnUiThread(() -> setContent(content));
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }).start();
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

}