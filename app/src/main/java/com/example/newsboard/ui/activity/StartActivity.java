package com.example.newsboard.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.example.newsboard.R;
import com.example.newsboard.util.TokenUtils;


public class StartActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initApp();

        new Thread(() -> {
            try {
                textView = findViewById(R.id.start_page_text);
                for (int i = 0; i <= 10; i++) {
                    textView.setAlpha(0.1F * i);
                    Thread.sleep(200);
                }
                Thread.sleep(500);
                startActivity(new Intent(this, MainActivity.class));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void initApp() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        TokenUtils.initTokenUtils(pref);
    }

}