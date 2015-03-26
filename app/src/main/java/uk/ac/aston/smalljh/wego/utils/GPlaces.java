package uk.ac.aston.smalljh.wego.utils;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by joshuahugh on 09/03/15.
 */
public class GPlaces implements Parcelable {

    private String id;
    private String icon;
    private String name;
    private String vicinity;
    private Double latitude;
    private Double longitude;

    private long locID = 0;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getVicinity() {
        return vicinity;
    }
    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public static GPlaces jsonToPontoReferencia(JSONObject pontoReferencia) {
        try {
            GPlaces result = new GPlaces();
            JSONObject geometry = (JSONObject) pontoReferencia.get("geometry");
            JSONObject location = (JSONObject) geometry.get("location");
            result.setLatitude((Double) location.get("lat"));
            result.setLongitude((Double) location.get("lng"));
            result.setIcon(pontoReferencia.getString("icon"));
            result.setName(pontoReferencia.getString("name"));
            result.setVicinity(pontoReferencia.getString("vicinity"));
            result.setId(pontoReferencia.getString("id"));
            return result;
        } catch (JSONException ex) {
           ex.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Place{" + "id=" + id + ", icon=" + icon + ", name=" + name + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeString(id);
        out.writeString(icon);
        out.writeString(name);
        out.writeString(vicinity);
        out.writeDouble(latitude);
        out.writeDouble(longitude);
    }

    public static final Parcelable.Creator<GPlaces> CREATOR
            = new Parcelable.Creator<GPlaces>() {
        public GPlaces createFromParcel(Parcel in) {
            return new GPlaces(in);
        }

        public GPlaces[] newArray(int size) {
            return new GPlaces[size];
        }
    };

    public GPlaces(Parcel in) {
        id = in.readString();
        icon = in.readString();
        name = in.readString();
        vicinity = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public GPlaces(String name, String vicinity,Double latitude, Double longitude, long locID) {

        this.name = name;
        this.vicinity = vicinity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.locID = locID;

    }

    public GPlaces() {}

    public long getLocID() {
        return locID;
    }
}
