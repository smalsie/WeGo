package uk.ac.aston.smalljh.wego;

/**
 * Created by joshuahugh on 09/11/14.
 */
public class PlaceItem {

    private String title;
    private String city;
    private String time;
    private int pic;

    public PlaceItem(String title, String city, String time, int pic) {
        this.title = title;
        this.city = city;
        this.time = time;
        this.pic = pic;
    }

    public String getTime() {
        return time;
    }

    public String getCity() {
        return city;
    }

    public String getTitle() {
        return title;
    }

    public int getPic() { return pic; }
}
