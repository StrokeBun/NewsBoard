package com.example.newsboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

public class ArticleActivity extends AppCompatActivity {

    private TextView titleTextView = null;
    private TextView authorTextView = null;
    private TextView punishedTimeTextView = null;
    private TextView contentTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        test();
    }


    private void init() {
        setContentView(R.layout.activity_article);
        titleTextView = findViewById(R.id.article_title);
        authorTextView = findViewById(R.id.article_author);
        punishedTimeTextView = findViewById(R.id.punished_time);
        contentTextView = findViewById(R.id.article_content);
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

    private void test() {
        setTitle("aaa");
        setAuthor("bzzb");
        setPunishedTime("2020/11/15");
        setContent("阿巴阿巴");
    }

}