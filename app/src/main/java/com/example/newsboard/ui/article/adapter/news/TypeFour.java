package com.example.newsboard.ui.article.adapter.news;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.newsboard.R;
import com.example.newsboard.model.NewsView;
import com.example.newsboard.ui.HomeFragment;

/**
 * <pre>
 *     author : Su Songfeng
 *     e-mail : 1986553865@qq.com
 *     time   : 2020/11/21 11:26
 *     desc   : 新闻排版类型4，包含标题、作者和4张图片
 *     version: 1.0
 * </pre>
 */
class TypeFour extends BaseType {
    ImageView newsImage1;
    ImageView newsImage2;
    ImageView newsImage3;
    ImageView newsImage4;

    public TypeFour(@NonNull View itemView) {
        super(itemView);
        newsImage1 = itemView.findViewById(R.id.news_image);
        newsImage2 = itemView.findViewById(R.id.news_image1);
        newsImage3 = itemView.findViewById(R.id.news_image2);
        newsImage4 = itemView.findViewById(R.id.news_image3);
    }

    @Override
    public void load(NewsView newsView) {
        super.load(newsView);
        Glide.with(HomeFragment.context).load("https://img-blog.csdnimg.cn/20201122205843681.jpeg").into(newsImage1);
        Glide.with(HomeFragment.context).load("https://img-blog.csdnimg.cn/20201122213841886.jpeg").into(newsImage2);
        Glide.with(HomeFragment.context).load("https://img-blog.csdnimg.cn/2020112221392235.jpeg").into(newsImage3);
        Glide.with(HomeFragment.context).load("https://img-blog.csdnimg.cn/20201122213943681.jpeg").into(newsImage4);
    }
}
