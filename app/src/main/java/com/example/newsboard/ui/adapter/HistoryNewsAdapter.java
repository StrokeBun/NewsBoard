package com.example.newsboard.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsboard.R;
import com.example.newsboard.model.NewsView;

import java.util.List;



public class HistoryNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NewsView> newsViewList;

    public HistoryNewsAdapter(List<NewsView> newsViewList) {
        this.newsViewList = newsViewList;
    }

    private static class TypeHistory extends RecyclerView.ViewHolder {
        TextView newsText;
        TextView publishText;

        public TypeHistory(@NonNull View itemView) {
            super(itemView);
            newsText = (TextView) itemView.findViewById(R.id.news_text);
            publishText = (TextView) itemView.findViewById(R.id.publish_message);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_history, null, false);
        return new TypeHistory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewsView newsView = newsViewList.get(position);
        String title = newsView.getNews().getTitle();
        ((TypeHistory) holder).newsText.setText(title);
        ((TypeHistory) holder).publishText.setText(newsView.getPublishMessage());
    }

    @Override
    public int getItemCount() {
        return newsViewList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

}
