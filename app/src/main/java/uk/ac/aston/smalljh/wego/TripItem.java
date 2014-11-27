package uk.ac.aston.smalljh.wego;

/**
 * Created by joshuahugh on 09/11/14.
 */
public class TripItem {

    private String title;
    private String[] companions;
    private String time;
    private int pic;

    public TripItem(String title, String[] companions, String time, int pic) {
        this.title = title;
        this.companions = companions;
        this.time = time;
        this.pic = pic;
    }

    public String getTime() {
        return time;
    }

    public String getCompanions() {

        String s = "With ";

        for(int i = 0; i < companions.length; i++) {
            s+= companions[i] + ", ";
        }

        s = s.substring(0, s.length()-2);


        return s;
    }

    public String getTitle() {
        return title;
    }

    public int getPic() { return pic; }
}
