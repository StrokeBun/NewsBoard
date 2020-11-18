package com.example.newsboard.feature.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.newsboard.base.BaseActivity;
import com.example.newsboard.R;


public class HomeActivity extends BaseActivity {

    public static final String ACTION_LOG_OUT = "com.example.newsboard.LOG_OUT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(view -> {
            Intent intent = new Intent(ACTION_LOG_OUT);
            sendBroadcast(intent);
        });
    }

}