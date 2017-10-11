package ipca.example.newsam;

import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        String urlString=getIntent().getStringExtra(NewsListActivity.NEWS_PAPER_URL);
        String title=getIntent().getStringExtra(NewsListActivity.NEWS_PAPER_TITLE);

        setTitle(title);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        webView=(WebView)findViewById(R.id.webViewPost);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(urlString);

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                progressBar.setProgress(newProgress);

                if (newProgress>99){
                    progressBar.setVisibility(View.GONE);
                }else
                {
                    progressBar.setVisibility(View.VISIBLE);
                }


            }
        });
    }
}
