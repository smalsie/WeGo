package uk.ac.aston.smalljh.wego;

/**
 * Created by joshuahugh on 09/11/14.
 */
public class HomeItem {

    private String title;
    private String time;

    public HomeItem(String title, String time) {
        this.title = title;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }
}
