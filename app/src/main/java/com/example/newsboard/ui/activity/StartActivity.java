package com.example.newsboard.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.newsboard.R;

import java.util.Calendar;

public class StartActivity extends AppCompatActivity {

    private static final String GOOD_MORNING = "早上好, 打工人";
    private static final String GOOD_NOON = "中午好，打工人";
    private static final String GOOD_AFTERNOON = "下午好，打工人";
    private static final String GOOD_EVENING = "晚上好, 打工人";

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        textView = findViewById(R.id.start_page_text);
        setText();

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                startActivity(new Intent(this, MainActivity.class));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void setText() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        Log.e("hour", String.valueOf(hour));
        if (hour >= 14 && hour <= 17) {
            textView.setText(GOOD_AFTERNOON);
        } else if (hour >= 5 && hour <= 9) {
            textView.setText(GOOD_MORNING);
        } else if (hour >= 10 && hour <= 13) {
            textView.setText(GOOD_NOON);
        } else {
            textView.setText(GOOD_EVENING);
        }
    }
}