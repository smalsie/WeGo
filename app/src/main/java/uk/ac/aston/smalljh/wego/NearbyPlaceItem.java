package uk.ac.aston.smalljh.wego;

/**
 * Created by joshuahugh on 09/11/14.
 */
public class NearbyPlaceItem {

    private String place;
    private String distance;
    private int stars;
    private int pic;

    public NearbyPlaceItem(String place, String distance, int stars, int pic) {
        this.place = place;
        this.distance = distance;
        this.stars = stars;
        this.pic = pic;
    }

    public String getStars() {
        return stars + "/5 stars";
    }

    public String getDistance() {
        return distance;
    }

    public String getPlace() {
        return place;
    }

    public int getPic() { return pic; }
}
