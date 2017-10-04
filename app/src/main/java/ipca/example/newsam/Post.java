package ipca.example.newsam;

import java.util.Date;

/**
 * Created by lourenco on 04/10/17.
 */

public class Post {

    String title;
    String url;
    Date datePub;
    String imageLink;

    public Post(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDatePub() {
        return datePub;
    }

    public void setDatePub(Date datePub) {
        this.datePub = datePub;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", datePub=" + datePub +
                ", imageLink='" + imageLink + '\'' +
                '}';
    }
}
