package uk.ac.aston.smalljh.wego;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aston.smalljh.wego.fragments.UserInfo;
import uk.ac.aston.smalljh.wego.utils.DatabaseHelper;
import uk.ac.aston.smalljh.wego.utils.GPlaces;
import uk.ac.aston.smalljh.wego.utils.ImageItem;

/**
 * Created by joshuahugh on 09/11/14.
 */
public class TripItem {

    private String title;
    private List<String> companions;
    private String startDate, endDate;

    private List<Note> notes;

    private List<PlaceItem> places;

    private long id;
    private GPlaces gPlaces;
    private long imageID = 0;
    private ArrayList<ImageItem> images;

    private ImageItem pic;
    public TripItem(long id, String title, GPlaces gPlaces, String startDate, String endDate, ImageItem pic, long imageID) {
        this.id = id;
        this.title = title;
        this.gPlaces = gPlaces;
        this.startDate = startDate;
        this.endDate = endDate;

        if(pic != null)
            this.pic = pic;


        this.imageID = imageID;

        images = new ArrayList<>();


        this.places = places;

        notes = new ArrayList<Note>();
        companions = new ArrayList<String>();
        places = new ArrayList<PlaceItem>();

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

    public void addPlace(PlaceItem placeItem) {
        places.add(placeItem);
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartDate() {
        return startDate;
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

        c.put(dh.tripsCompanionsId, id);
        c.put(dh.tripsCompanionsName, name);

        SQLiteDatabase db = dh.getWritableDatabase();

        long insertID = dh.insert(dh.tripsCompanionsTable, c);

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

        c.put(dh.tripsNotesNotesId, insertID);
        c.put(dh.tripsNotesTripId, id);

        dh.insert(dh.tripsNotesTable, c);

        notes.add(new Note(name, note));
    }

    public void putPlace(long placeID, Context context) {
        DatabaseHelper dh = new DatabaseHelper(context);

        PlaceItem placeItem = dh.getPlace(placeID, dh.getReadableDatabase());

        putPlace(placeItem, context);
    }

    public void putPlace(PlaceItem placeItem, Context context) {

        DatabaseHelper dh = new DatabaseHelper(context);

        ContentValues c = new ContentValues();

        c.put(dh.tripsPlaceplaceId, placeItem.getID());
        c.put(dh.tripsPlaceTripId, id);

        long insertID = dh.insert(dh.tripsPlacesTable, c);


        places.add(placeItem);
    }

    public GPlaces getGPlace() {
        return gPlaces;
    }

    public long getImageID() {
        return imageID;
    }

    public List<PlaceItem> getPlaces() {
        return places;
    }



    public void addImage(ImageItem image) {
        images.add(image);
    }

    public void putImage(String path, String tag, Context context, long latitude, long longitude) {
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

        c.put(dh.tripsImagesImageId, insertID);
        c.put(dh.tripsImagesId, id);

        long imageID = dh.insert(dh.tripsImagesTable, c);

        Bitmap bitmap = BitmapFactory.decodeFile(path);
        images.add(new ImageItem(bitmap, tag, imageID, latitude, longitude));
    }

    public ArrayList<ImageItem> getImages() {
        return images;
    }
}
