package com.example.newsboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.newsboard.util.HttpUtil;
import com.example.newsboard.util.TokenUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class ArticleActivity extends AppCompatActivity {

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
        getArticle("bytetalk_01", true);
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


    private void init() {
        setContentView(R.layout.activity_article);
        titleTextView = findViewById(R.id.article_title);
        authorTextView = findViewById(R.id.article_author);
        punishedTimeTextView = findViewById(R.id.punished_time);
        contentTextView = findViewById(R.id.article_content);
        setTitle("aaa");
        setAuthor("bzzb");
        setPunishedTime("2020/11/15");
        setContent("阿巴阿巴");
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