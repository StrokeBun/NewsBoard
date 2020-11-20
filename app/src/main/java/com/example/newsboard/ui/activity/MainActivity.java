package com.example.newsboard.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.newsboard.R;
import com.example.newsboard.model.News;
import com.example.newsboard.base.BaseActivity;
import com.example.newsboard.ui.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


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


    public void myItemClick(View view){
        int position = HomeFragment.getRecyclerView().getChildAdapterPosition(view);
        News news = HomeFragment.getNewsList().get(position);
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra(ArticleActivity.EXTRA_ID, news.getId());
        intent.putExtra(ArticleActivity.EXTRA_TITLE, news.getTitle());
        intent.putExtra(ArticleActivity.EXTRA_AUTHOR, news.getAuthor());
        intent.putExtra(ArticleActivity.EXTRA_PUBlISH_TIME, news.getPublishTime());
        startActivity(intent);
    }

}