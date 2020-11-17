package com.example.newsboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsboard.feature.article.ArticleActivity;

public class IntentTestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_test);
        Button button = findViewById(R.id.send_intent_button);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(IntentTestActivity.this, ArticleActivity.class);
            intent.putExtra(ArticleActivity.EXTRA_ID, "bytetalk_01");
            intent.putExtra(ArticleActivity.EXTRA_AUTHOR, "bytedance");
            intent.putExtra(ArticleActivity.EXTRA_TITLE, "2020字节跳动全球员工摄影大赛邀请函");
            intent.putExtra(ArticleActivity.EXTRA_PUBlISH_TIME, "2020年10月7日");
            startActivity(intent);
        });
    }
}
