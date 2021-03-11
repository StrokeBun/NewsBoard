package com.example.newsboard.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.example.newsboard.R;
import com.example.newsboard.model.News;
import com.example.newsboard.model.NewsView;
import com.example.newsboard.base.BaseActivity;
import com.example.newsboard.ui.article.activity.ArticleActivity;
import com.example.newsboard.ui.login.LoginActivity;
import com.example.newsboard.util.TokenUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

/**
 * <pre>
 *     author : Su Songfeng
 *     e-mail : 1986553865@qq.com
 *     time   : 2020/11/16 18:05
 *     desc   : 主页
 *     version: 1.0
 * </pre>
 */
public class MainActivity extends BaseActivity {

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_mine)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        TokenUtils.initTokenUtils(pref);
    }

    /**
     * 新闻主页用户点击的处理方法，跳转到相应文章页面
     * @param view View
     */
    public void myItemClick(View view){
        int position = HomeFragment.getRecyclerView().getChildAdapterPosition(view);
        NewsView newsView = HomeFragment.getNewsViewList().get(position);
        if (TokenUtils.isNotLogin()) { // 用户未登录跳转到登录页面
            startActivityForResult(new Intent(this, LoginActivity.class), LoginActivity.LOGIN_REQUEST_CODE);
        } else { // 传递文章数据
            Intent intent = new Intent(this, ArticleActivity.class);
            intent.putExtra(News.EXTRA_NEWS, newsView.getNews());
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}