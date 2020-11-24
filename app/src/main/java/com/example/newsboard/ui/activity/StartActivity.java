package com.example.newsboard.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.newsboard.R;
import com.example.newsboard.util.TokenUtils;


public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initApp();

        new Thread(() -> {
            try {
                Thread.sleep(2000);
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