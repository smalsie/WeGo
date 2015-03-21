package uk.ac.aston.smalljh.wego;

/**
 * Created by joshuahugh on 13/02/15.
 */

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import uk.ac.aston.smalljh.wego.fragments.UserInfo;
import uk.ac.aston.smalljh.wego.utils.GPlaces;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "WeGO";

    //User Table
    public static final String userTable = "User";
    public static final String userID = "UserID";
    public static final String userFullName = "FullName";
    public static final String userName = "Username";
    public static final String userPass = "Password";

    //Trips Table
    public static final String tripsTable = "Trips";
    public static final String tripsId = "TripID";
    public static final String tripsUserId = "UserID";
    public static final String tripsTitle = "TripTitle";
    public static final String tripsLocation = "TripLocation";
    public static final String tripsStartDate = "TripStartDate";
    public static final String tripsEndDate = "TripEndDate";

    //TripImages
    public static final String tripsImagesTable = "TripImages";
    public static final String tripsImagesId = "TripID";
    public static final String tripsImagesImageId = "ImageID";

    //TripCompanions
    public static final String tripsCompanionsTable = "TripCompanions";
    public static final String tripsCompanionsId = "TripID";
    //public static final String tripsCompanionsUserId = "UserID";
    public static final String tripsCompanionsName = "UserName";

    //TripNotes
    public static final String tripsNotesTable = "TripNotes";
    public static final String tripsNotesNotesId = "NotesID";
    public static final String tripsNotesTripId = "TripID";

    //Notes
    public static final String notesTable = "Notes";
    public static final String notesTableID = "NoteID";
    public static final String notesTitle = "Title";
    public static final String notesNote = "Note";

    //Images
    public static final String imagesTable = "Images";
    public static final String imagesId = "ImageID";
    public static final String imagesUserId = "UserID";
    public static final String imagesSRC = "ImageSRC";

    //Places
    public static final String placesTable = "Places";
    public static final String placesId = "PlaceID";
    public static final String placesUserId = "UserID";
    public static final String placesTitle = "PlaceTitle";
    public static final String placesLocationID = "PlaceLocationID";
    public static final String placesDate = "PlaceStartDate";

    //PlaceImages
    public static final String placesImagesTable = "PlacesImages";
    public static final String placesImagesPlaceId = "PlaceID";
    public static final String placesImagesImageId = "ImageID";

    //PlaceCompanions
    public static final String placesCompanionsTable = "PlacesCompanions";
    public static final String placesCompanionsPlaceId = "PlaceID";
    public static final String placesCompanionsCompanionName = "CompanionName";

    //PlaceNotes
    public static final String placesNotesTable = "PlaceNotes";
    public static final String placesNotesNotesId = "NotesID";
    public static final String placesNotesPlaceId = "PlaceID";

    //Location
    public static final String locationTable = "Location";
    public static final String locationID = "LocationID";
    public static final String LocationLatitude = "Latitude";
    public static final String LocationLongitude = "Longitude";
    public static final String LocationName = "Name";
    public static final String LocationVicinity = "Vicinity";

    public Context c;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);

        c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create user table
        String sql = "CREATE TABLE " + userTable + " (" + userID
                + " INTEGER PRIMARY KEY  AUTOINCREMENT, "
                + userFullName + " TEXT, "
                + userName + " TEXT, "
                + userPass + " TEXT)";
        Log.i("DBHELPER", sql);
        db.execSQL(sql);

        // create trips table
        sql = "CREATE TABLE " + tripsTable + " (" + tripsId
                + " INTEGER PRIMARY KEY  AUTOINCREMENT, "
                + tripsUserId + " INTEGER, "
                + tripsTitle + " TEXT, "
                + tripsLocation + " TEXT, "
                + tripsStartDate + " TEXT, "
                + tripsEndDate + " TEXT)";
        Log.i("DBHELPER", sql);
        db.execSQL(sql);

        // create TripImages table
        sql = "CREATE TABLE " + tripsImagesTable + " (" + tripsImagesId
                + " INTEGER, "
                + tripsImagesImageId + " INTEGER)";
        Log.i("DBHELPER", sql);
        db.execSQL(sql);


        // create TripCompanions table
        sql = "CREATE TABLE " + tripsCompanionsTable + " (" + tripsCompanionsId
                + " INTEGER, "
                + tripsCompanionsName + " TEXT)";
        Log.i("DBHELPER", sql);
        db.execSQL(sql);

        // create Images table
        sql = "CREATE TABLE " + imagesTable + " (" + imagesId
                + " INTEGER, "
                + imagesUserId + " INTEGER, "
                + imagesSRC + " TEXT)";
        Log.i("DBHELPER", sql);
        db.execSQL(sql);

        // create places table
        sql = "CREATE TABLE " + placesTable + " (" + placesId
                + " INTEGER PRIMARY KEY  AUTOINCREMENT, "
                + placesUserId + " INTEGER, "
                + placesTitle + " TEXT, "
                + placesLocationID + " INTEGER, "
                + placesDate + " TEXT)";
        Log.i("DBHELPER", sql);
        db.execSQL(sql);

        // create PlaceImages table
        sql = "CREATE TABLE " + placesImagesTable + " (" + placesImagesPlaceId
                + " INTEGER, "
                + placesImagesImageId + " INTEGER)";
        Log.i("DBHELPER", sql);
        db.execSQL(sql);

        // create PlaceCompanions table
        sql = "CREATE TABLE " + placesCompanionsTable + " (" + placesCompanionsPlaceId
                + " INTEGER, "
                + placesCompanionsCompanionName + " TEXT)";
        Log.i("DBHELPER", sql);
        db.execSQL(sql);

        // create TripNotes table
        sql = "CREATE TABLE " + tripsNotesTable + " (" + tripsNotesNotesId
                + " INTEGER, "
                + tripsNotesTripId + " INTEGER)";
        Log.i("DBHELPER", sql);
        db.execSQL(sql);

        // create PlaceNotes table
        sql = "CREATE TABLE " + placesNotesTable + " (" + placesNotesNotesId
                + " INTEGER, "
                + placesNotesPlaceId + " INTEGER)";
        Log.i("DBHELPER", sql);
        db.execSQL(sql);

        // create Notes table
        sql = "CREATE TABLE " + notesTable + " (" + notesTableID
                + " INTEGER PRIMARY KEY  AUTOINCREMENT, "
                + notesTitle + " TEXT, "
                + notesNote + " TEXT)";
        Log.i("DBHELPER", sql);
        db.execSQL(sql);

        // create Location table
        sql = "CREATE TABLE " + locationTable + " (" + locationID
                + " INTEGER PRIMARY KEY  AUTOINCREMENT, "
                + LocationLatitude + " DOUBLE, "
                + LocationLongitude + " DOUBLE, "
                + LocationName + " TEXT, "
                + LocationVicinity + " TEXT)";
        Log.i("DBHELPER", sql);
        db.execSQL(sql);

        insertInitialFeeds(db, "smalsie", "1234");
        insertInitialFeeds(db, "smalsiee", "12334");
    }

    private void insertInitialFeeds(SQLiteDatabase db, String username, String password) {

        ContentValues values = new ContentValues();
        //values.put(userID, id);
        values.put(userName, username);
        values.put(userPass, password);

// Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                userTable,
                null,
                values);
        //db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + userTable);
        onCreate(db);

    }


    public long insert(String table, ContentValues inserts) {

        SQLiteDatabase db = getWritableDatabase();
        /*String sql = "INSERT INTO " + table + " (";

        ArrayList<String> keys = new ArrayList<String>();
        ArrayList<Object> values = new ArrayList<Object>();

        Iterator it = inserts.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            keys.add(pair.getKey().toString());
            values.add(pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }

        String keyString = "";

        for(String s : keys)
            keyString += "`" + s + "`,";


        keyString = keyString.substring(0, keyString.length()-1);


        sql += keyString + ") VALUES(";

        String valueString = "";

        for(Object o : values)
            valueString += "'" + o.toString() + "',";


        valueString = valueString.substring(0, valueString.length()-1);


        sql += valueString + ")";

        Log.i("SQL", sql);

        */

        return db.insert(table, null, inserts);
    }

    public ArrayList<TripItem> getTrips(SQLiteDatabase db) {
        ArrayList<TripItem> trips = new ArrayList<TripItem>();

        int savedUserID = new UserInfo(c).getUserID();

        String[] projection = {
                tripsId,
                tripsTitle,
                tripsLocation,
                tripsStartDate,
                tripsEndDate,
        };

        String whereClause = tripsUserId + "=?";

        String[] whereArgs = new String[]{
                savedUserID + "",
        };

        String sortOrder =
                tripsId;


        Cursor c = db.query(
                tripsTable,  // The table to query
                projection,                               // The columns to return
                whereClause,                                // The columns for the WHERE clause
                whereArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (c.getCount() != 0) {

            if (c.moveToFirst()) {
                do {

                    long tripID = c.getLong(
                            c.getColumnIndexOrThrow(tripsId)
                    );

                    String title = c.getString(
                            c.getColumnIndexOrThrow(tripsTitle)
                    );

                    String location = c.getString(
                            c.getColumnIndexOrThrow(tripsLocation)
                    );

                    String endDate = c.getString(
                            c.getColumnIndexOrThrow(tripsStartDate)
                    );

                    String startDate = c.getString(
                            c.getColumnIndexOrThrow(tripsEndDate)
                    );

                    TripItem t = new TripItem(title, location, startDate, endDate);

                    List<String> companions = getCompanions(tripID, db);

                    List<Note> notes = getNotes(tripID, db);

                    for(String comp : companions)
                        t.addCompanion(comp);

                    for(Note note : notes)
                        t.addNote(note);


                    trips.add(t);


                } while (c.moveToNext());
            }



        }

        return trips;

    }

    public ArrayList<PlaceItem> getPlaces(SQLiteDatabase db) {
        ArrayList<PlaceItem> places = new ArrayList<PlaceItem>();

        int savedUserID = new UserInfo(c).getUserID();

        String[] projection = {
                placesId,
                placesTitle,
                placesLocationID,
                placesDate,
        };

        String whereClause = placesUserId + "=?";

        String[] whereArgs = new String[]{
                savedUserID + "",
        };

        String sortOrder =
                placesId;


        Cursor c = db.query(
                placesTable,  // The table to query
                projection,                               // The columns to return
                whereClause,                                // The columns for the WHERE clause
                whereArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (c.getCount() != 0) {

            if (c.moveToFirst()) {
                do {

                    long placeID = c.getLong(
                            c.getColumnIndexOrThrow(placesId)
                    );

                    String title = c.getString(
                            c.getColumnIndexOrThrow(placesTitle)
                    );

                    long locationID = c.getLong(
                            c.getColumnIndexOrThrow(placesLocationID)
                    );

                    String date = c.getString(
                            c.getColumnIndexOrThrow(placesDate)
                    );

                    PlaceItem p = new PlaceItem(placeID, title, getLocation(locationID), date);

                    List<String> companions = getCompanionsPlaces(placeID, db);

                    List<Note> notes = getNotesPlaces(placeID, db);

                    for(String comp : companions)
                        p.addCompanion(comp);

                    for(Note note : notes)
                        p.addNote(note);


                    places.add(p);


                } while (c.moveToNext());
            }



        }

        return places;

    }

    public PlaceItem getPlace(long placeID, SQLiteDatabase db) {
        int savedUserID = new UserInfo(c).getUserID();

        String[] projection = {
                placesId,
                placesTitle,
                placesLocationID,
                placesDate,
        };

        String whereClause = placesUserId + "=? AND " + placesId + "=?";

        String[] whereArgs = new String[]{
                savedUserID + "",
                placeID + "",
        };

        String sortOrder =
                placesId;


        Cursor c = db.query(
                placesTable,  // The table to query
                projection,                               // The columns to return
                whereClause,                                // The columns for the WHERE clause
                whereArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (c.getCount() == 0)
            return null;
        else {

           c.moveToFirst();

                    String title = c.getString(
                            c.getColumnIndexOrThrow(placesTitle)
                    );

                    long locationID = c.getLong(
                            c.getColumnIndexOrThrow(placesLocationID)
                    );

                    String date = c.getString(
                            c.getColumnIndexOrThrow(placesDate)
                    );

                    PlaceItem p = new PlaceItem(placeID, title, getLocation(locationID), date);

                    List<String> companions = getCompanionsPlaces(placeID, db);

                    List<Note> notes = getNotesPlaces(placeID, db);

                    for(String comp : companions)
                        p.addCompanion(comp);

                    for(Note note : notes)
                        p.addNote(note);

                return p;

            }

    }

    public List<String> getCompanions(long id, SQLiteDatabase db) {
        List<String> companions = new ArrayList<String>();

        String[] projection = {
                tripsCompanionsName,
        };

        String whereClause = tripsCompanionsId + "=?";

        String[] whereArgs = new String[]{
                id + "",
        };

        String sortOrder =
                tripsCompanionsName;


        Cursor c = db.query(
                tripsCompanionsTable,  // The table to query
                projection,                               // The columns to return
                whereClause,                                // The columns for the WHERE clause
                whereArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (c.getCount() != 0) {

            if (c.moveToFirst()) {
                do {

                    String companion = c.getString(
                            c.getColumnIndexOrThrow(tripsCompanionsName)
                    );

                    companions.add(companion);

                } while (c.moveToNext());
            }



        }

        return companions;
    }

    public List<String> getCompanionsPlaces(long id, SQLiteDatabase db) {
        List<String> companions = new ArrayList<String>();

        String[] projection = {
                placesCompanionsCompanionName,
        };

        String whereClause = placesCompanionsPlaceId + "=?";

        String[] whereArgs = new String[]{
                id + "",
        };

        String sortOrder =
                placesCompanionsCompanionName;


        Cursor c = db.query(
                placesCompanionsTable,  // The table to query
                projection,                               // The columns to return
                whereClause,                                // The columns for the WHERE clause
                whereArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (c.getCount() != 0) {

            if (c.moveToFirst()) {
                do {

                    String companion = c.getString(
                            c.getColumnIndexOrThrow(placesCompanionsCompanionName)
                    );

                    companions.add(companion);

                } while (c.moveToNext());
            }



        }

        return companions;
    }


    public List<Note> getNotes(long id, SQLiteDatabase db) {
        List<Note> notes = new ArrayList<Note>();

        String[] projection = {
                tripsNotesNotesId,
        };

        String whereClause = tripsNotesTripId + "=?";

        String[] whereArgs = new String[]{
                id + "",
        };

        String sortOrder =
                tripsNotesNotesId;


        Cursor c = db.query(
                tripsNotesTable,  // The table to query
                projection,                               // The columns to return
                whereClause,                                // The columns for the WHERE clause
                whereArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (c.getCount() != 0) {

            if (c.moveToFirst()) {
                do {

                    long noteIDToGet = c.getLong(
                            c.getColumnIndexOrThrow(tripsNotesNotesId)
                    );

                    notes.add(getNote(noteIDToGet, db));

                } while (c.moveToNext());
            }



        }

        return notes;
    }

    public List<Note> getNotesPlaces(long id, SQLiteDatabase db) {
        List<Note> notes = new ArrayList<Note>();

        String[] projection = {
                placesNotesNotesId,
        };

        String whereClause = placesNotesPlaceId + "=?";

        String[] whereArgs = new String[]{
                id + "",
        };

        String sortOrder =
                placesNotesNotesId;


        Cursor c = db.query(
                placesNotesTable,  // The table to query
                projection,                               // The columns to return
                whereClause,                                // The columns for the WHERE clause
                whereArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (c.getCount() != 0) {

            if (c.moveToFirst()) {
                do {

                    long noteIDToGet = c.getLong(
                            c.getColumnIndexOrThrow(placesNotesNotesId)
                    );

                    notes.add(getNote(noteIDToGet, db));

                } while (c.moveToNext());
            }



        }

        return notes;
    }

    private Note getNote(long id, SQLiteDatabase db) {

        String[] projection = {
                notesTitle,
                notesNote,
        };

        String whereClause = notesTableID + "=?";

        String[] whereArgs = new String[] {
                id + "",
        };

        String sortOrder =
                notesTableID;


        Cursor c = db.query(
                notesTable,  // The table to query
                projection,                               // The columns to return
                whereClause,                                // The columns for the WHERE clause
                whereArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if(c.getCount() != 0) {

            c.moveToFirst();

            String title = c.getString(
                    c.getColumnIndexOrThrow(notesTitle)
            );
            String note = c.getString(
                    c.getColumnIndexOrThrow(notesNote)
            );

            return new Note(title, note);

        }

        return null;

    }

    public GPlaces getLocation(long locationIDToGet) {
        String[] projection = {
                LocationLatitude,
                LocationLongitude,
                LocationName,
                LocationVicinity,
        };

        SQLiteDatabase db = getReadableDatabase();

        String whereClause = locationID + "=?";

        String[] whereArgs = new String[] {
                locationIDToGet + "",
        };

        String sortOrder =
                locationID;


        Cursor c = db.query(
                locationTable,  // The table to query
                projection,                               // The columns to return
                whereClause,                                // The columns for the WHERE clause
                whereArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if(c.getCount() != 0) {

            c.moveToFirst();

            Double lat = c.getDouble(
                    c.getColumnIndexOrThrow(LocationLatitude)
            );
            Double lng = c.getDouble(
                    c.getColumnIndexOrThrow(LocationLongitude)
            );

            String name = c.getString(
                    c.getColumnIndexOrThrow(LocationName)
            );

            String vicinity = c.getString(
                    c.getColumnIndexOrThrow(LocationVicinity)
            );

            return new GPlaces(name, vicinity, lat, lng);

        }

        return null;



    }

    public ArrayList<GPlaces> getGPlaces() {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<GPlaces> gPlaces = new ArrayList<GPlaces>();

        ArrayList<PlaceItem> places = getPlaces(db);

        for(PlaceItem p : places)
            gPlaces.add(p.getGPlace());

        return gPlaces;
    }
}