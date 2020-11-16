package com.example.newsboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class IntentTestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_test);
        Button button = findViewById(R.id.send_intent_button);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(IntentTestActivity.this, ArticleActivity.class);
            intent.putExtra(ArticleActivity.ID, "bytetalk_01");
            intent.putExtra(ArticleActivity.AUTHOR, "原名梁志彬");
            intent.putExtra(ArticleActivity.TITLE, "BZZB");
            intent.putExtra(ArticleActivity.PUblISH_TIME, "2020/11/17");
            startActivity(intent);
        });
    }
}
