package com.example.newsboard.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsboard.R;
import com.example.newsboard.model.NewsView;
import com.example.newsboard.ui.fragment.HomeFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: NewsAdapter
 * @Package: UIController
 * @Description: Adapter fo News
 * @author: Susongfeng
 * @date: 2020/11/21 11:26
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ZERO = 0;
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private static final int TYPE_THREE = 3;
    private static final int TYPE_FOUR = 4;
    private static final int TYPE_HISTORY = 5;
    private static final Map<Integer, Integer> typeLayoutMap = new HashMap<Integer, Integer>(){
        {
            put(TYPE_ZERO, R.layout.type_zero);
            put(TYPE_ONE, R.layout.type_one);
            put(TYPE_TWO, R.layout.type_two);
            put(TYPE_THREE, R.layout.type_three);
            put(TYPE_FOUR, R.layout.type_four);
            put(TYPE_HISTORY, R.layout.type_history);
        }
    };

    private List<NewsView> mNewsViewList;

    public NewsAdapter(List<NewsView> newsViewList) {
        mNewsViewList = newsViewList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(typeLayoutMap.get(viewType), null, false);
        return ViewHolderFactory.getInstance(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewsView newsView = mNewsViewList.get(position);
        ((BaseType) holder).load(newsView);
    }

    @Override
    public int getItemCount() {
        return mNewsViewList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (mNewsViewList.get(position).getType()) {
            case 0:
                return TYPE_ZERO;
            case 1:
                return TYPE_ONE;
            case 2:
                return TYPE_TWO;
            case 3:
                return TYPE_THREE;
            case 4:
                return TYPE_FOUR;
            case 5:
                return TYPE_HISTORY;
            default:
        }
        return super.getItemViewType(position);
    }

    /**
     * 各种新闻排版的基类
     */
    private static class BaseType extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        TextView newsText;
        TextView publishText;

        public BaseType(@NonNull View itemView) {
            super(itemView);
            newsText = itemView.findViewById(R.id.news_text);
            publishText = itemView.findViewById(R.id.publish_message);
        }

        /**
         * 加载资源，继承BaseType的类需要override此方法
         * @param newsView 新闻排版数据
         */
        public void load(NewsView newsView) {
            newsText.setText(newsView.getNews().getTitle());
            publishText.setText(newsView.getPublishMessage());
        }
    }

    private static class TypeZero extends BaseType {
        public TypeZero(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void load(NewsView newsView) {
            super.load(newsView);
        }
    }

    private static class TypeCom extends BaseType {  // type 1 & 2 & 3
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

    private static class TypeFour extends BaseType {
        ImageView newsImage;
        ImageView newsImage1;
        ImageView newsImage2;
        ImageView newsImage3;

        public TypeFour(@NonNull View itemView) {
            super(itemView);
            newsImage = itemView.findViewById(R.id.news_image);
            newsImage1 = itemView.findViewById(R.id.news_image1);
            newsImage2 = itemView.findViewById(R.id.news_image2);
            newsImage3 = itemView.findViewById(R.id.news_image3);
        }

        @Override
        public void load(NewsView newsView) {
            super.load(newsView);
            Glide.with(HomeFragment.context).load("https://img-blog.csdnimg.cn/20201122205843681.jpeg").into(newsImage);
            Glide.with(HomeFragment.context).load("https://img-blog.csdnimg.cn/20201122213841886.jpeg").into(newsImage1);
            Glide.with(HomeFragment.context).load("https://img-blog.csdnimg.cn/2020112221392235.jpeg").into(newsImage2);
            Glide.with(HomeFragment.context).load("https://img-blog.csdnimg.cn/20201122213943681.jpeg").into(newsImage3);
        }
    }

    private static class TypeHistory extends BaseType {

        public TypeHistory(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void load(NewsView newsView) {
            super.load(newsView);
        }
    }

    private static class ViewHolderFactory{
        private static RecyclerView.ViewHolder getInstance(View view,int viewType) {
            RecyclerView.ViewHolder holder = null;
            switch (viewType) {
                case TYPE_ZERO:
                    holder = new TypeZero(view);
                    break;
                case TYPE_ONE:
                case TYPE_TWO:
                case TYPE_THREE:
                    holder = new TypeCom(view);
                    break;
                case TYPE_FOUR:
                    holder = new TypeFour(view);
                    break;
                case TYPE_HISTORY:
                    holder = new TypeHistory(view);
                default:
                    break;
            }
            return holder;
        }
    }

}
