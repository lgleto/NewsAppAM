package ipca.example.newsam;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lourenco on 27/09/17.
 */

public class NewsListActivity extends AppCompatActivity {

    public static final String NEWS_PAPER_TITLE = "news_paper_title";
    public static final String NEWS_PAPER_URL = "news_paper_url";

    List<Post> postList=new ArrayList<>(); //model

    ListView listView;
    PostAdapter postAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        listView=(ListView)findViewById(R.id.listViewPost);
        postAdapter=new PostAdapter();
        listView.setAdapter(postAdapter);

        String title = getIntent().getStringExtra(NEWS_PAPER_TITLE);
        String urlString = getIntent().getStringExtra(NEWS_PAPER_URL);
        setTitle(title);

        HttpFetchData httpFetchData=new HttpFetchData();
        httpFetchData.setOnHttpResponse(new HttpListener() {
            @Override
            public void onHttpResponse(List<Post> posts) {
                postList=posts;
                postAdapter.notifyDataSetChanged();


            }
        });
        httpFetchData.execute(urlString,null,null);

    }

    class PostAdapter extends BaseAdapter implements View.OnClickListener{

        LayoutInflater layoutInflater;

        public PostAdapter(){
            layoutInflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return postList.size();
        }

        @Override
        public Object getItem(int i) {
            return postList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view==null){
                view=layoutInflater.inflate(R.layout.row_post,null);
            }
            TextView textView = (TextView)view.findViewById(R.id.textViewPostRow);
            textView.setText(postList.get(i).getTitle());

            view.setTag(new Integer(i));
            view.setClickable(true);
            view.setOnClickListener(this);



            return view;
        }

        @Override
        public void onClick(View view) {
            int position = (Integer) view.getTag();

            Log.d("newsam", postList.get(position).getTitle() );

            Intent intent = new Intent(NewsListActivity.this, WebViewActivity.class);
            intent.putExtra(NEWS_PAPER_TITLE,postList.get(position).getTitle());
            intent.putExtra(NEWS_PAPER_URL,postList.get(position).getUrl());
            startActivity(intent);

        }
    }

}
