package ipca.example.newsam;

import android.content.Intent;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;

    String urlString="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        urlString=getIntent().getStringExtra(NewsListActivity.NEWS_PAPER_URL);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Shared by Coolest News App");
            shareIntent.putExtra(Intent.EXTRA_TEXT, urlString);
            startActivity(Intent.createChooser(shareIntent, "Partilhar"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
