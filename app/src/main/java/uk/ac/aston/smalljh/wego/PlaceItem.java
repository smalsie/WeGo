package uk.ac.aston.smalljh.wego;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshuahugh on 09/11/14.
 */
public class PlaceItem implements Parcelable {

    private String title;
    private String location;
    private String date;
    private int pic;
    private List<String> companions;
    private List<Note> notes;

    public PlaceItem(String title, String location, String date) {
        this.title = title;
        this.location = location;
        this.date = date;

        notes = new ArrayList<Note>();
        companions = new ArrayList<String>();
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public int getPic() { return pic; }

    public void addCompanion(String companion) {
        companions.add(companion);
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeString(location);
        out.writeString(date);
        out.writeInt(pic);
        out.writeList(companions);
        out.writeList(notes);
    }

    public static final Parcelable.Creator<PlaceItem> CREATOR
            = new Parcelable.Creator<PlaceItem>() {
        public PlaceItem createFromParcel(Parcel in) {
            return new PlaceItem(in);
        }

        public PlaceItem[] newArray(int size) {
            return new PlaceItem[size];
        }
    };

    public PlaceItem(Parcel in) {
        title = in.readString();
        location = in.readString();
        date = in.readString();
        pic = in.readInt();
        companions = in.readArrayList(null);
        notes = in.readArrayList(null);
    }

    public List<String> getCompanions() {
        return companions;
    }

    public List<Note> getNotes() {
        return notes;
    }
}
