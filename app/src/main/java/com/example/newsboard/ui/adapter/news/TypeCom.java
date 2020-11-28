package com.example.newsboard.ui.adapter.news;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.newsboard.R;
import com.example.newsboard.model.NewsView;

/**
 * <pre>
 *     author : Su Songfeng
 *     e-mail : 1986553865@qq.com
 *     time   : 2020/11/21 11:26
 *     desc   : type为1、2、3对应的类型，包含标题、作者和一张图片
 *     version: 1.0
 * </pre>
 */
class TypeCom extends BaseType {  // type 1 & 2 & 3
    ImageView newImage;

    public TypeCom(@NonNull View itemView) {
        super(itemView);
        newImage = itemView.findViewById(R.id.news_image);
    }

    @Override
    public void load(NewsView newsView) {
        super.load(newsView);
        newImage.setImageResource(newsView.getCover());
    }
}
