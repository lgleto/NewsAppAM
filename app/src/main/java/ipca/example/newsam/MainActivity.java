package ipca.example.newsam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonPublico;
    Button buttonObservador;
    Button buttonRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPublico   =(Button)findViewById(R.id.buttonPublico    );
        buttonObservador=(Button)findViewById(R.id.buttonObservador );
        buttonRecord    =(Button)findViewById(R.id.buttonRecord     );

        buttonPublico   .setOnClickListener(this);
        buttonObservador.setOnClickListener(this);
        buttonRecord    .setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,NewsListActivity.class);
        switch (view.getId()){
            case R.id.buttonPublico:
                intent.putExtra(NewsListActivity.NEWS_PAPER_TITLE, "Publico");
                intent.putExtra(NewsListActivity.NEWS_PAPER_URL, "https://feeds.feedburner.com/PublicoRSS");

                startActivity(intent);
                break;
            case R.id.buttonObservador:
                break;
            case R.id.buttonRecord:
                break;
        }
    }
}
