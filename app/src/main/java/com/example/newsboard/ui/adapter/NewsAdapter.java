package com.example.newsboard.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsboard.R;
import com.example.newsboard.model.NewsView;

import java.util.List;

/**
 * @Title: NewsAdapter
 * @Package: UIController
 * @Description: Adapter fo News
 * @author: Susongfeng
 * @date: 2020/11/16 19:36
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ZERO = 0;
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private static final int TYPE_THREE = 3;
    private static final int TYPE_FOUR = 4;
    private List<NewsView> mNewsViewList;

    public NewsAdapter(List<NewsView> newsViewList) {
        mNewsViewList = newsViewList;
    }

    static class Type_Zero extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView newsText;
        TextView publishText;

        public Type_Zero(@NonNull View itemView) {
            super(itemView);
            newsText = (TextView) itemView.findViewById(R.id.news_text);
            publishText = (TextView) itemView.findViewById(R.id.publish_message);
        }

        @Override
        public void onClick(View v) {

        }

    }

    static class Type_Com extends RecyclerView.ViewHolder {  // type 1 & 2 & 3
        TextView newsText;
        TextView publishText;
        ImageView newImage;

        public Type_Com(@NonNull View itemView) {
            super(itemView);
            newsText = (TextView) itemView.findViewById(R.id.news_text);
            publishText = (TextView) itemView.findViewById(R.id.publish_message);
            newImage = (ImageView) itemView.findViewById(R.id.news_image);
        }
    }

    static class Type_Four extends RecyclerView.ViewHolder {
        TextView newsText;
        TextView publishText;
        ImageView newsImage;
        ImageView newsImage1;
        ImageView newsImage2;
        ImageView newsImage3;

        public Type_Four(@NonNull View itemView) {
            super(itemView);
            newsText = (TextView) itemView.findViewById(R.id.news_text);
            publishText = (TextView) itemView.findViewById(R.id.publish_message);
            newsImage = (ImageView) itemView.findViewById(R.id.news_image);
            newsImage1 = (ImageView) itemView.findViewById(R.id.news_image1);
            newsImage2 = (ImageView) itemView.findViewById(R.id.news_image2);
            newsImage3 = (ImageView) itemView.findViewById(R.id.news_image3);
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_ZERO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_zero, null, false);
                final Type_Zero holder = new Type_Zero(view);
                return holder;
            case TYPE_ONE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_one, null, false);
                final Type_Com holder1 = new Type_Com(view);
                return holder1;
            case TYPE_TWO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_two, null, false);
                final Type_Com holder2 = new Type_Com(view);
                return holder2;
            case TYPE_THREE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_three, null, false);
                final Type_Com holder3 = new Type_Com(view);
                return holder3;
            case TYPE_FOUR:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_four, null, false);
                final Type_Four holder4 = new Type_Four(view);
                return holder4;
            default:
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewsView newsView = mNewsViewList.get(position);
        String title = newsView.getNews().getTitle();
        if (holder instanceof Type_Zero) {
            ((Type_Zero) holder).newsText.setText(title);
            ((Type_Zero) holder).publishText.setText(newsView.getPublishMessage());
        } else if (holder instanceof Type_Four) {
            ((Type_Four) holder).newsText.setText(title);
            ((Type_Four) holder).publishText.setText(newsView.getPublishMessage());
            ((Type_Four) holder).newsImage.setImageResource(R.drawable.event_02);
            ((Type_Four) holder).newsImage1.setImageResource(R.drawable.event_02);
            ((Type_Four) holder).newsImage2.setImageResource(R.drawable.event_02);
            ((Type_Four) holder).newsImage3.setImageResource(R.drawable.event_02);
        } else if (holder instanceof Type_Com) {
            ((Type_Com) holder).newsText.setText(title);
            ((Type_Com) holder).publishText.setText(newsView.getPublishMessage());
            ((Type_Com) holder).newImage.setImageResource(newsView.getCover());
        }
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
            default:
        }
        return super.getItemViewType(position);
    }


}
