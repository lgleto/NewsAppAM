package ipca.example.newsam;

import android.os.AsyncTask;
import android.util.Log;

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
 * Created by lourenco on 04/10/17.
 */

public class HttpFetchData extends AsyncTask <String, Void, String>{

    List listeners=new ArrayList();

    public synchronized void setOnHttpResponse(HttpListener httpListener){
        listeners.add(httpListener);
    }

    public synchronized void removeOnHttpResponse(HttpListener httpListener){
        listeners.remove(httpListener);
    }

    private synchronized void fireSuccessHttpResponse(String s){
        for (Object l:listeners){
            HttpListener httpListener=(HttpListener) l;
            httpListener.onHttpResponse(s);
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        String result="";
        HttpURLConnection urlConnection = null;
        try {
            URL url=new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");


            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line =  bufferedReader.readLine())!=null){
                total.append(line).append('\n');
            }
            result = total.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //textViewText.setText(s);
        //Log.d("newsam",s);
        fireSuccessHttpResponse(s);
    }
}
