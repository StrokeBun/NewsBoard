package com.example.newsboard.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsboard.R;
import com.example.newsboard.util.TokenUtils;

/**
 * <pre>
 *     author : Zhong DeFeng
 *     e-mail : 1756809298@qq.com
 *     time   : 2020/11/24 18:05
 *     desc   : 启动页面
 *     version: 1.0
 * </pre>
 */
public class StartActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        textView = findViewById(R.id.start_page_text);
        imageView = findViewById(R.id.img);

        initApp();
        createAnimation();
    }

    /**
     * 初始化应用
     */
    private void initApp() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        TokenUtils.initTokenUtils(pref);
    }

    /**
     * 设置启动页面动画
     */
    private void createAnimation() {
        new Thread(() -> {
            try {
                // 设置主页逐渐出现
                for (int i = 0; i <= 20; i++) {
                    Float alpha = 0.05F * i;
                    textView.setAlpha(alpha);
                    imageView.setAlpha(alpha);
                    Thread.sleep(100);
                }
                Thread.sleep(500);
                startActivity(new Intent(this, MainActivity.class));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}