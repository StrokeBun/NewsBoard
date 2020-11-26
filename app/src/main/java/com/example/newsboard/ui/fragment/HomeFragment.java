package com.example.newsboard.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    private static List<NewsView> newsViewList = new ArrayList<>();
    private static RecyclerView recyclerView;
    public static Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initNews();
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        NewsAdapter adapter = new NewsAdapter(newsViewList);
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * @Description:Init news
     * @author： Susongfeng
     * @Date: 2020/11/15
     */
    private void initNews(){
        String json = readJson("metadata.json");
        initNews(json);
    }

    /**
     * Init news by json string
     * @author： Susongfeng
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
     * @author： Susongfeng
     * @param fileName 文件名
     * @return Json字符串
     */
    private String readJson(String fileName){
        try (InputStream inputStream = getResources().getAssets().open(fileName)) {
            int length = inputStream.available();
            byte[] buffer = new byte[length];
            inputStream.read(buffer);
            String content = new String(buffer, "UTF-8");
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析json字符串为NewsView对象并添加到列表中
     * @author： Susongfeng
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
                if(type == 0) {
                    NewsView newsView = new NewsView(news, type);
                    newsViewList.add(newsView);
                    continue;
                } else if(type == 4){
                    String covers = jsonObject.getString("covers");
                } else{
                    String cover = jsonObject.getString("cover");
                    NewsView newsView = null;
                    switch (cover){
                        case "tancheng":
                            newsView = new NewsView(news, type, R.drawable.tancheng);
                            break;
                        case "event_02":
                            newsView = new NewsView(news, type, R.drawable.event_02);
                            break;
                        case "teambuilding_04":
                            newsView = new NewsView(news, type, R.drawable.teambuilding);
                            break;
                        default:
                    }
                    if (newsView != null) {
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