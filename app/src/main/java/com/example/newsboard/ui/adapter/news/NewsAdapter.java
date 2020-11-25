package com.example.newsboard.ui.adapter.news;

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
 * <pre>
 *     author : Su Songfeng
 *     e-mail : 1986553865@qq.com
 *     time   : 2020/11/21 11:26
 *     desc   : Adapter fo News
 *     version: 1.0
 * </pre>
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ZERO = 0;
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private static final int TYPE_THREE = 3;
    private static final int TYPE_FOUR = 4;
    private static final int TYPE_HISTORY = 5;
    private static final Map<Integer, Integer> viewTypeLayoutMap = new HashMap<Integer, Integer>(){
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
        View view = LayoutInflater.from(parent.getContext()).inflate(viewTypeLayoutMap.get(viewType), null, false);
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
     * 简单工厂，根据viewType返回对应的ViewHolder
     */
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
