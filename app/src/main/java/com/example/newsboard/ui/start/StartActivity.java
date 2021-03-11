package com.example.newsboard.ui.start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsboard.R;
import com.example.newsboard.ui.MainActivity;
import com.example.newsboard.ui.HomeFragment;
import com.example.newsboard.util.FileUtils;
import com.example.newsboard.util.TokenUtils;

import java.io.IOException;
import java.io.InputStream;

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

    // 本地待加载文件名
    private static final String LOCAL_FILENAME = "metadata.json";
    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        textView = findViewById(R.id.start_page_text);
        imageView = findViewById(R.id.img);

        createAnimation();
        initApp();
    }

    /**
     * 初始化应用
     */
    private void initApp() {
        loadNewsLocal(LOCAL_FILENAME);
    }


    /**
     * 根据本地配置文件加载新闻数据
     * @param fileName 本地配置文件名
     */
    private void loadNewsLocal(String fileName) {
        String json = readJson(fileName);
        HomeFragment.initNews(json);
    }

    /**
     * Read json file in assets directory.
     * @author： Susongfeng
     * @param fileName 文件名
     * @return Json字符串
     */
    private String readJson(String fileName){
        try (InputStream inputStream = this.getResources().getAssets().open(fileName)) {
            return FileUtils.readFile(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
                    Thread.sleep(50);
                }
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}