package com.example.newsboard.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsboard.R;
import com.example.newsboard.model.News;
import com.example.newsboard.ui.adapter.NewsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static List<News> newsList = new ArrayList<>();
    private String content;
    private static RecyclerView recyclerView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initNews();
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        NewsAdapter adapter = new NewsAdapter(newsList);
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 此处需要清除newList，否则将导致重复页面
        newsList.clear();
    }

    // TODO: 目前只能使用静态方法传递参数，待解决
    public static List<News> getNewsList() {
        return newsList;
    }

    public static RecyclerView getRecyclerView() {
        return recyclerView;
    }

    private void initNews(){
        doReadJson();
        doNews();
        News news = new News("test","9月18日淀山湖户外团建",4,"vc mobile team","2020年9月7日");
        newsList.add(news);
    }

    public void doReadJson(){
        /**
         * @Description:Read Json File
         * @author： Susongfeng
         * @Param: []
         * @return: void
         * @Date: 2020/11/15
         */
        try {
            InputStream inputStream = getResources().getAssets().open("metadata.json");
            int length = inputStream.available();
            byte[] buffer = new byte[length];
            inputStream.read(buffer);
            content = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doNews(){
        /**
         * @Description: Json to News
         * @author： Susongfeng
         * @Param: []
         * @return: void
         * @Date: 2020/11/15
         */
        try {
            JSONArray jsonArray = new JSONArray(content);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String title = jsonObject.getString("title");
                int type = jsonObject.getInt("type");
                String author = jsonObject.getString("author");
                String publishTime = jsonObject.getString("publishTime");
                if(type == 0) {
                    News news = new News(id, title,type,author,publishTime);
                    newsList.add(news);
                    continue;
                }else if(type == 4){
                    String covers = jsonObject.getString("covers");
                }else{
                    String cover = jsonObject.getString("cover");
                    switch (cover){
                        case "tancheng":
                            News news = new News(id, title,type,R.drawable.tancheng,author,publishTime);
                            newsList.add(news);
                            break;
                        case "event_02":
                            News news1 = new News(id, title,type,R.drawable.event_02,author,publishTime);
                            newsList.add(news1);
                            break;
                        case "teambuilding_04":
                            News news2 = new News(id, title,type,R.drawable.teambuilding,author,publishTime);
                            newsList.add(news2);
                            break;
                        default:
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}