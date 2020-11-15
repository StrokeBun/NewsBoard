package com.example.newsboard;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.newsboard.util.TokenUtil;


public class LogoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(view -> {
//            Intent intent = new Intent("com.example.infoboard.LOG_OUT");
//            sendBroadcast(intent);
            Toast.makeText(LogoutActivity.this, TokenUtil.getToken(), Toast.LENGTH_SHORT).show();
        });
    }

}