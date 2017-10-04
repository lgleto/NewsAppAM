package ipca.example.newsam;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lourenco on 27/09/17.
 */

public class NewsListActivity extends AppCompatActivity {

    public static final String NEWS_PAPER_TITLE = "news_paper_title";
    public static final String NEWS_PAPER_URL = "news_paper_url";

    TextView textViewText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        textViewText=(TextView)findViewById(R.id.textViewText);
        String title = getIntent().getStringExtra(NEWS_PAPER_TITLE);
        String urlString = getIntent().getStringExtra(NEWS_PAPER_URL);
        setTitle(title);
        //textViewText.setText(url);


        HttpFetchData httpFetchData=new HttpFetchData();
        httpFetchData.setOnHttpResponse(new HttpListener() {
            @Override
            public void onHttpResponse(String webText) {
                textViewText.setText(webText);
            }
        });
        httpFetchData.execute(urlString,null,null);







    }
}
