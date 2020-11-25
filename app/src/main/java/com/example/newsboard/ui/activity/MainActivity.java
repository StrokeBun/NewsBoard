package com.example.newsboard.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.newsboard.R;
import com.example.newsboard.model.News;
import com.example.newsboard.model.NewsView;
import com.example.newsboard.base.BaseActivity;
import com.example.newsboard.ui.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
    }

    /**
     * 新闻主页用户点击的处理方法，跳转到相应文章页面
     * @param view View
     */
    public void myItemClick(View view){
        int position = HomeFragment.getRecyclerView().getChildAdapterPosition(view);
        NewsView newsView = HomeFragment.getNewsViewList().get(position);
        News news = newsView.getNews();
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra(News.EXTRA_NEWS, news);
        startActivity(intent);
    }

}