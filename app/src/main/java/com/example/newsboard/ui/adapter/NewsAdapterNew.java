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
public class NewsAdapterNew extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ZERO = 0;
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private static final int TYPE_THREE = 3;
    private static final int TYPE_FOUR = 4;
    private static final Map<Integer, Integer> typeLayoutMap = new HashMap<Integer, Integer>(){
        {
            put(TYPE_ZERO, R.layout.type_zero);
            put(TYPE_ONE, R.layout.type_one);
            put(TYPE_TWO, R.layout.type_two);
            put(TYPE_THREE, R.layout.type_three);
            put(TYPE_FOUR, R.layout.type_four);
        }
    };

    private List<NewsView> mNewsViewList;

    public NewsAdapterNew(List<NewsView> newsViewList) {
        mNewsViewList = newsViewList;
    }

    private static class TypeBase extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        TextView newsText;
        TextView publishText;

        public TypeBase(@NonNull View itemView) {
            super(itemView);
            newsText = (TextView) itemView.findViewById(R.id.news_text);
            publishText = (TextView) itemView.findViewById(R.id.publish_message);
        }
    }

    private static class Type_Zero extends TypeBase {
        public Type_Zero(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static class Type_Com extends TypeBase {  // type 1 & 2 & 3
        ImageView newImage;

        public Type_Com(@NonNull View itemView) {
            super(itemView);
            newImage = (ImageView) itemView.findViewById(R.id.news_image);
        }
    }

    private static class Type_Four extends TypeBase {
        ImageView newsImage;
        ImageView newsImage1;
        ImageView newsImage2;
        ImageView newsImage3;

        public Type_Four(@NonNull View itemView) {
            super(itemView);
            newsImage = (ImageView) itemView.findViewById(R.id.news_image);
            newsImage1 = (ImageView) itemView.findViewById(R.id.news_image1);
            newsImage2 = (ImageView) itemView.findViewById(R.id.news_image2);
            newsImage3 = (ImageView) itemView.findViewById(R.id.news_image3);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(typeLayoutMap.get(viewType), null, false);
        return ViewHolderFactory.getInstance(view, viewType);
    }

    private static class ViewHolderFactory{
        private static RecyclerView.ViewHolder getInstance(View view,int viewType) {
            RecyclerView.ViewHolder holder = null;
            switch (viewType) {
                case TYPE_ZERO:
                    holder = new Type_Zero(view);
                    break;
                case TYPE_ONE:
                case TYPE_TWO:
                case TYPE_THREE:
                    holder = new Type_Com(view);
                    break;
                case TYPE_FOUR:
                    holder = new Type_Four(view);
                    break;
                default:
                    break;
            }
            return holder;
        }

        private ViewHolderFactory() {

        }
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
