package com.example.newsboard.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.newsboard.R;
import com.example.newsboard.UIController.News;
import com.example.newsboard.base.ui.home.HomeFragment;
import com.example.newsboard.feature.article.ArticleActivity;
import com.example.newsboard.feature.user.LoginActivity;
import com.example.newsboard.util.TokenUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }


    public void myItemClick(View view){  //点击事件
        int position = HomeFragment.getRecyclerView().getChildAdapterPosition(view);  // 获取当前点击的item 在链表中的位置
        News news = HomeFragment.getNewsList().get(position);
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra(ArticleActivity.EXTRA_ID, news.getId());
        intent.putExtra(ArticleActivity.EXTRA_TITLE, news.getTitle());
        intent.putExtra(ArticleActivity.EXTRA_AUTHOR, news.getAuthor());
        intent.putExtra(ArticleActivity.EXTRA_PUBlISH_TIME, news.getPublishTime());
        startActivity(intent);
    }

}