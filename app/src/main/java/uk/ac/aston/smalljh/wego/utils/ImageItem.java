package uk.ac.aston.smalljh.wego.utils;

import android.graphics.Bitmap;

/**
 * Created by joshuahugh on 23/03/15.
 */
public class ImageItem {
    private Bitmap image;
    private String title;
    private long imageID;
    private long latitude, longitude;

    public ImageItem(Bitmap image, String title, long imageID, long latitude, long longitude) {
        super();
        this.image = image;
        this.title = title;
        this.imageID = imageID;

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getID() {
        return imageID;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }
}
