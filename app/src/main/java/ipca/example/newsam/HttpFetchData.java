package ipca.example.newsam;

import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by lourenco on 04/10/17.
 */

public class HttpFetchData extends AsyncTask <String, Void, List<Post>>{

    List listeners=new ArrayList();

    public synchronized void setOnHttpResponse(HttpListener httpListener){
        listeners.add(httpListener);
    }

    public synchronized void removeOnHttpResponse(HttpListener httpListener){
        listeners.remove(httpListener);
    }

    private synchronized void fireSuccessHttpResponse(List<Post> posts){
        for (Object l:listeners){
            HttpListener httpListener=(HttpListener) l;
            httpListener.onHttpResponse(posts);
        }
    }

    @Override
    protected List<Post> doInBackground(String... strings) {

        List<Post> postList=new ArrayList<>();

        HttpURLConnection urlConnection = null;
        try {
            URL url=new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");


            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=factory.newDocumentBuilder();
            InputStream inputStream = urlConnection.getInputStream();
            Document doc=builder.parse(inputStream);

            NodeList nodeList=doc.getElementsByTagName("item");
            for (int i=0; i < nodeList.getLength(); i++){

                Element element=(Element)nodeList.item(i);
                NodeList nodeTitle=element.getElementsByTagName("title");
                String strTitle= nodeTitle.item(0).getTextContent();
                NodeList nodeUrl=element.getElementsByTagName("link");
                String strUrl= nodeUrl.item(0).getTextContent();
                Post post=new Post(strTitle,strUrl);

                postList.add(post);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return postList;
    }

    @Override
    protected void onPostExecute(List<Post> posts) {
        super.onPostExecute(posts);
        //textViewText.setText(s);
        //Log.d("newsam",s);
        fireSuccessHttpResponse(posts);
    }
}
