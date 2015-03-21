package uk.ac.aston.smalljh.wego;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aston.smalljh.wego.utils.GPlaces;

/**
 * Created by joshuahugh on 09/11/14.
 */
public class PlaceItem implements Parcelable {

    private long id;
    private String title;
   // private String location;
    private String date;
    private int pic;
    private List<String> companions;
    private List<Note> notes;
    private GPlaces gPlaces;

    public PlaceItem(long id, String title, GPlaces gPlaces, String date) {
        this.id = id;
        this.title = title;
        this.gPlaces = gPlaces;
        this.date = date;

        notes = new ArrayList<Note>();
        companions = new ArrayList<String>();

        Log.i("Place", id + "");

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
        out.writeLong(id);
        out.writeString(title);
        //out.writeString(location);
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
        id = in.readLong();
        title = in.readString();
       // location = in.readString();
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
}
