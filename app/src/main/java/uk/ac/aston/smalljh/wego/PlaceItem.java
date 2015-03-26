package uk.ac.aston.smalljh.wego;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.ac.aston.smalljh.wego.fragments.UserInfo;
import uk.ac.aston.smalljh.wego.utils.DatabaseHelper;
import uk.ac.aston.smalljh.wego.utils.GPlaces;
import uk.ac.aston.smalljh.wego.utils.ImageItem;

/**
 * Created by joshuahugh on 09/11/14.
 */
public class PlaceItem implements Parcelable {

    private long id;
    private String title;
   // private String location;
    private String date;
    private ImageItem pic;
    private List<String> companions;
    private List<Note> notes;
    private GPlaces gPlaces;
    private long imageID = 0;
    private ArrayList<ImageItem> images;

    public PlaceItem(long id, String title, GPlaces gPlaces, String date, ImageItem pic, long imageID) {
        this.id = id;
        this.title = title;
        this.gPlaces = gPlaces;
        this.date = date;
        if(pic != null)
            this.pic = pic;

        notes = new ArrayList<Note>();
        companions = new ArrayList<String>();
        this.imageID = imageID;

        images = new ArrayList<ImageItem>();



    }

    /*public String getLocation() {
        return location;
    }*/

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public ImageItem getPic() { return pic; }

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
        out.writeLong(id);
        out.writeString(title);
        //out.writeString(location);
        out.writeString(date);
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
        id = in.readLong();
        title = in.readString();
       // location = in.readString();
        date = in.readString();
        companions = in.readArrayList(null);
        notes = in.readArrayList(null);
    }

    public List<String> getCompanions() {
        return companions;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public long getID() {
        return id;
    }

    public void putCompanion(String name, Context context) {

        DatabaseHelper dh = new DatabaseHelper(context);

        ContentValues c = new ContentValues();

        c.put(dh.placesCompanionsPlaceId, id);
        c.put(dh.placesCompanionsCompanionName, name);

        SQLiteDatabase db = dh.getWritableDatabase();

        long insertID = dh.insert(dh.placesCompanionsTable, c);

        Log.i("Place Insert", insertID + "");

        companions.add(name);
    }


    public void putNote(String name, String note,  Context context) {

        DatabaseHelper dh = new DatabaseHelper(context);

        ContentValues c = new ContentValues();

        c.put(dh.notesTitle, name);
        c.put(dh.notesNote, note);

        long insertID = dh.insert(dh.notesTable, c);

        Log.i("Note Insert", insertID + "");

        c = new ContentValues();

        c.put(dh.placesNotesNotesId, insertID);
        c.put(dh.placesNotesPlaceId, id);

        dh.insert(dh.placesNotesTable, c);

        notes.add(new Note(name, note));
    }

    public GPlaces getGPlace() {
        return gPlaces;
    }


    public void addImage(ImageItem image) {
        images.add(image);
    }

    public void putImage(String path, String tag, long latitude, long longitude, Context context) {
        DatabaseHelper dh = new DatabaseHelper(context);

        ContentValues c = new ContentValues();

        UserInfo ui = new UserInfo(context);
        c.put(dh.imagesUserId, ui.getUserID());
        c.put(dh.imagesSRC, path);
        c.put(dh.imagesTAG, tag);
        c.put(dh.imagesLocationLatitude, latitude);
        c.put(dh.imagesLocationLongitude, longitude);



        long insertID = dh.insert(dh.imagesTable, c);

        c = new ContentValues();

        c.put(dh.placesImagesImageId, insertID);
        c.put(dh.placesImagesPlaceId, id);

        long imageID = dh.insert(dh.placesImagesTable, c);

        Bitmap bitmap = BitmapFactory.decodeFile(path);;

        images.add(new ImageItem(bitmap, tag, imageID, latitude, longitude));
    }

    public ArrayList<ImageItem> getImages() {
        return images;
    }
    public long getImageID() {
        return imageID;
    }


}
