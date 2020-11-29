package com.example.newsboard.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsboard.R;
import com.example.newsboard.model.News;
import com.example.newsboard.model.NewsView;
import com.example.newsboard.ui.adapter.news.NewsAdapter;
import com.example.newsboard.util.FileUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     author : Su Songfeng
 *     e-mail : 1986553865@qq.com
 *     time   : 2020/11/16 18:05
 *     desc   : 新闻主页Frament
 *     version: 1.0
 * </pre>
 */
public class HomeFragment extends Fragment {

    // 新闻列表
    private static final List<NewsView> newsViewList = new ArrayList<>();
    private static RecyclerView recyclerView;
    public static Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initNews();
        recyclerView = root.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        NewsAdapter adapter = new NewsAdapter(newsViewList);
        recyclerView.setAdapter(adapter);
        return root;
    }

    /**
     * Init news
     */
    private void initNews(){
        String json = readJson("metadata.json");
        initNews(json);
    }

    /**
     * Init news by json string
     * @param json json 字符串
     */
    public static void initNews(String json) {
        if (newsViewList.isEmpty()) {
            doNews(json);
            News news = new News("teamBuilding_09", "9月18日淀山湖户外团建", "vc mobile team", "2020年9月7日");
            NewsView newsView = new NewsView(news, 4);
            newsViewList.add(newsView);
        }
    }

    /**
     * Read json file
     * @param fileName Json文件名
     * @return json字符串
     */
    private String readJson(String fileName){
        try (InputStream inputStream = getResources().getAssets().open(fileName)) {
            return FileUtils.readFile(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析json字符串为NewsView对象并添加到列表中
     * @param json json字符串
     */
    private static void doNews(String json) {
        newsViewList.clear();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0, len = jsonArray.length(); i < len; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String title = jsonObject.getString("title");
                String author = jsonObject.getString("author");
                String publishTime = jsonObject.getString("publishTime");
                News news = new News(id, title, author, publishTime);
                int type = jsonObject.getInt("type");
                if (type == 0) {
                    NewsView newsView = new NewsView(news, type);
                    newsViewList.add(newsView);
                } else {
                    String cover = jsonObject.getString("cover");
                    Map<String, Integer> map = new HashMap<String, Integer>(){
                        {
                            put("tancheng", R.drawable.tancheng);
                            put("event_02", R.drawable.event_02);
                            put("teambuilding_04", R.drawable.teambuilding);
                        }
                    };
                    if (map.get(cover) != null) {
                        NewsView newsView = new NewsView(news, type, map.get(cover));
                        newsViewList.add(newsView);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static List<NewsView> getNewsViewList() {
        return newsViewList;
    }

    public static RecyclerView getRecyclerView() {
        return recyclerView;
    }
}