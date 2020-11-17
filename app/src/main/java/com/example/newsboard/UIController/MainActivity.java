package com.example.newsboard.UIController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import com.example.newsboard.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<News> newsList = new ArrayList<>();
    private String content;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNews();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        NewsAdapter adapter = new NewsAdapter(newsList);
        recyclerView.setAdapter(adapter);
    }

    public void myItemClick(View view){  //点击事件
        int position = recyclerView.getChildAdapterPosition(view);  // 获取当前点击的item 在链表中的位置
        Log.d("position","Pos is " + position);  // 当前展示顺序是json文件中的顺序
        Toast.makeText(MainActivity.this,"You Touch is "+newsList.get(position).getTitle(),Toast.LENGTH_SHORT).show();
    }

    private void initNews(){
        doReadJson();
        doNews();
        News news = new News("9月18日淀山湖户外团建",4,"vc mobile team","2020年9月7日");
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
            Log.d("Json",content);
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
            Log.d("Json",""+jsonArray.length());
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                int type = jsonObject.getInt("type");
                String author = jsonObject.getString("author");
                String publishTime = jsonObject.getString("publishTime");
                if(type == 0) {
                    News news = new News(title,type,author,publishTime);
                    newsList.add(news);
                    continue;
                }else if(type == 4){
                    String covers = jsonObject.getString("covers");
                    Log.d("Json",title+type+covers);
                }else{
                    String cover = jsonObject.getString("cover");
                    switch (cover){
                        case "tancheng":
                            News news = new News(title,type,R.drawable.tancheng,author,publishTime);
                            newsList.add(news);
                            break;
                        case "event_02":
                            News news1 = new News(title,type,R.drawable.event_02,author,publishTime);
                            newsList.add(news1);
                            break;
                        case "teambuilding_04":
                            News news2 = new News(title,type,R.drawable.teambuilding,author,publishTime);
                            newsList.add(news2);
                            break;
                        default:
                    }
                    Log.d("Json",title+type+cover);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}