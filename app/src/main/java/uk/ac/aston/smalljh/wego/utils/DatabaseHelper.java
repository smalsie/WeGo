package uk.ac.aston.smalljh.wego.utils;

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
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

import uk.ac.aston.smalljh.wego.Note;
import uk.ac.aston.smalljh.wego.PlaceItem;
import uk.ac.aston.smalljh.wego.TripItem;
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
    public static final String tripsLocationID = "TripLocationID";
    public static final String tripsStartDate = "TripStartDate";
    public static final String tripsEndDate = "TripEndDate";
    public static final String tripImage = "TripImage";

    //TripImages
    public static final String tripsImagesTable = "TripImages";
    public static final String tripsImagesTableID = "TripImagesID";
    public static final String tripsImagesId = "TripID";
    public static final String tripsImagesImageId = "ImageID";

    //TripCompanions
    public static final String tripsCompanionsTable = "TripCompanions";
    public static final String tripsCompanionsId = "TripID";
    //public static final String tripsCompanionsUserId = "UserID";
    public static final String tripsCompanionsName = "UserName";

    //TripPlaces
    public static final String tripsPlacesTable = "TripPlace";
    public static final String tripsPlaceID = "TripsPlaceID";
    public static final String tripsPlaceTripId = "TripID";
    public static final String tripsPlaceplaceId = "ImageID";

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
    public static final String imagesTAG = "ImageTAG";
    public static final String imagesLocationLatitude = "Latitude";
    public static final String imagesLocationLongitude = "Longitude";

    //Places
    public static final String placesTable = "Places";
    public static final String placesId = "PlaceID";
    public static final String placesUserId = "UserID";
    public static final String placesTitle = "PlaceTitle";
    public static final String placesLocationID = "PlaceLocationID";
    public static final String placesDate = "PlaceStartDate";
    public static final String placeImage = "PlaceImage";

    //PlaceImages
    public static final String placesImagesTable = "PlacesImages";
    public static final String placesImagesTableID = "PlacesImagesID";
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
                + tripsLocationID + " INTEGER, "
                + tripsStartDate + " TEXT, "
                + tripsEndDate + " TEXT, "
                + tripImage + " INTEGER)";
        Log.i("DBHELPER", sql);
        db.execSQL(sql);

        // create TripImages table
        sql = "CREATE TABLE " + tripsImagesTable + " (" + tripsImagesTableID
                + " INTEGER PRIMARY KEY  AUTOINCREMENT, "
                + tripsImagesImageId + " INTEGER, "
                + tripsImagesId + " INTEGER)";
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
                + " INTEGER PRIMARY KEY  AUTOINCREMENT, "
                + imagesUserId + " INTEGER, "
                + imagesTAG + " TEXT, "
                + imagesLocationLatitude + " DOUBLE, "
                + imagesLocationLongitude + " DOUBLE, "
                + imagesSRC + " TEXT)";
        Log.i("DBHELPER", sql);
        db.execSQL(sql);

        // create places table
        sql = "CREATE TABLE " + placesTable + " (" + placesId
                + " INTEGER PRIMARY KEY  AUTOINCREMENT, "
                + placesUserId + " INTEGER, "
                + placesTitle + " TEXT, "
                + placesLocationID + " INTEGER, "
                + placesDate + " TEXT, "
                + placeImage + " INTEGER)";
        Log.i("DBHELPER", sql);
        db.execSQL(sql);

        // create PlaceImages table
        sql = "CREATE TABLE " + placesImagesTable + " (" + placesImagesTableID
                + " INTEGER PRIMARY KEY  AUTOINCREMENT, "
                + placesImagesPlaceId + " INTEGER, "
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

        // create TripsPlace table
        sql = "CREATE TABLE " + tripsPlacesTable + " (" + tripsPlaceID
                + " INTEGER PRIMARY KEY  AUTOINCREMENT, "
                + tripsPlaceTripId + " INTEGER, "
                + tripsPlaceplaceId + " INTEGER)";
        Log.i("DBHELPER", sql);
        db.execSQL(sql);

        insertInitialFeeds(db, "test", "1234");
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

        long ret = db.insert(table, null, inserts);

        //db.close();

        return ret;
    }

    public ArrayList<TripItem> getTrips(SQLiteDatabase db) {
        ArrayList<TripItem> trips = new ArrayList<TripItem>();

        int savedUserID = new UserInfo(c).getUserID();

        String[] projection = {
                tripsId,
                tripsTitle,
                tripsLocationID,
                tripsStartDate,
                tripsEndDate,
                tripImage,
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

                    long locationID = c.getLong(
                            c.getColumnIndexOrThrow(tripsLocationID)
                    );

                    String startDate = c.getString(
                            c.getColumnIndexOrThrow(tripsStartDate)
                    );

                    String endDate = c.getString(
                            c.getColumnIndexOrThrow(tripsEndDate)
                    );

                    long imageID = c.getLong(
                            c.getColumnIndexOrThrow(tripImage)
                    );

                    ImageItem image = getImage(imageID);


                    TripItem t = new TripItem(tripID, title, getLocation(locationID), startDate, endDate, image, imageID);

                    List<String> companions = getCompanions(tripID, db);

                    List<Note> notes = getNotes(tripID, db);

                    List<ImageItem> images = getImages(tripID, db);

                    for (String comp : companions)
                        t.addCompanion(comp);

                    for (Note note : notes)
                        t.addNote(note);

                    for(ImageItem i : images)
                        t.addImage(i);


                    trips.add(t);


                } while (c.moveToNext());
            }


        }

        return trips;

    }

    public ArrayList<TripItem> getTripsAsc(SQLiteDatabase db) {
        ArrayList<TripItem> trips = new ArrayList<TripItem>();

        int savedUserID = new UserInfo(c).getUserID();

        String[] projection = {
                tripsId,
                tripsTitle,
                tripsLocationID,
                tripsStartDate,
                tripsEndDate,
                tripImage,
        };

        String whereClause = tripsUserId + "=?";

        String[] whereArgs = new String[]{
                savedUserID + "",
        };

        String sortOrder =
                tripsId + " ASC";


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

                    long locationID = c.getLong(
                            c.getColumnIndexOrThrow(tripsLocationID)
                    );

                    String startDate = c.getString(
                            c.getColumnIndexOrThrow(tripsStartDate)
                    );

                    String endDate = c.getString(
                            c.getColumnIndexOrThrow(tripsEndDate)
                    );

                    long imageID = c.getLong(
                            c.getColumnIndexOrThrow(tripImage)
                    );

                    ImageItem image = getImage(imageID);

                    TripItem t = new TripItem(tripID, title, getLocation(locationID), startDate, endDate, image, imageID);

                    List<String> companions = getCompanions(tripID, db);

                    List<Note> notes = getNotes(tripID, db);

                    for (String comp : companions)
                        t.addCompanion(comp);

                    for (Note note : notes)
                        t.addNote(note);


                    trips.add(t);


                } while (c.moveToNext());
            }


        }

        return trips;

    }

    public TripItem getTrip(SQLiteDatabase db, long tripID) {

        int savedUserID = new UserInfo(c).getUserID();

        String[] projection = {
                tripsId,
                tripsTitle,
                tripsLocationID,
                tripsStartDate,
                tripsEndDate,
                tripImage,
        };

        String whereClause = tripsUserId + "=? AND " + tripsId + "=?";

        String[] whereArgs = new String[]{
                savedUserID + "",
                tripID + "",
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

            c.moveToFirst();


                    String title = c.getString(
                            c.getColumnIndexOrThrow(tripsTitle)
                    );

                    long locationID = c.getLong(
                            c.getColumnIndexOrThrow(tripsLocationID)
                    );

                    String startDate = c.getString(
                            c.getColumnIndexOrThrow(tripsStartDate)
                    );

                    String endDate = c.getString(
                            c.getColumnIndexOrThrow(tripsEndDate)
                    );

                    long imageID = c.getLong(
                            c.getColumnIndexOrThrow(tripImage)
                    );

                    ImageItem image = getImage(imageID);

                    TripItem t = new TripItem(tripID, title, getLocation(locationID), startDate, endDate, image, imageID);

                    List<String> companions = getCompanions(tripID, db);

                    List<Note> notes = getNotes(tripID, db);

                    List<ImageItem> images = getImages(tripID, db);

                    for (String comp : companions)
                        t.addCompanion(comp);

                    for (Note note : notes)
                        t.addNote(note);

                    List<PlaceItem> places = getPlaces(db, tripID);

                    for(PlaceItem p: places)
                        t.addPlace(p);

                    for(ImageItem i : images)
                        t.addImage(i);

                    return t;

            }

        return null;

    }

    public ArrayList<PlaceItem> getPlacesSearch(SQLiteDatabase db, String search) {
        ArrayList<PlaceItem> places = new ArrayList<PlaceItem>();

        int savedUserID = new UserInfo(c).getUserID();

        String[] projection = {
                placesId,
                placesTitle,
                placesLocationID,
                placesDate,
                placeImage,
        };

        String whereClause = placesTitle + " LIKE ?";

        String[] whereArgs = new String[]{
                "%" + search + "%",
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

                    long imageID = c.getLong(
                            c.getColumnIndexOrThrow(placeImage)
                    );

                    ImageItem image = getImage(imageID);

                    PlaceItem p = new PlaceItem(placeID, title, getLocation(locationID), date, image,imageID);

                    List<String> companions = getCompanionsPlaces(placeID, db);

                    List<Note> notes = getNotesPlaces(placeID, db);

                    for (String comp : companions)
                        p.addCompanion(comp);

                    for (Note note : notes)
                        p.addNote(note);


                    places.add(p);


                } while (c.moveToNext());
            }


        }


        return places;
    }
    public ArrayList<PlaceItem> getPlaces(SQLiteDatabase db, long tripID) {
        ArrayList<PlaceItem> places = new ArrayList<PlaceItem>();

        int savedUserID = new UserInfo(c).getUserID();


        String[] projection = {
                tripsPlaceID,
        };

        String whereClause = tripsPlaceTripId + "=?";

        String[] whereArgs = new String[]{
                tripID + "",
        };

        String sortOrder =
                tripsPlaceID;


        Cursor c = db.query(
                tripsPlacesTable,  // The table to query
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
                            c.getColumnIndexOrThrow(tripsPlaceID)
                    );

                    places.add(getPlace(placeID, db));


                } while (c.moveToNext());
            }


        }



        return places;

    }

    public ArrayList<PlaceItem> getPlaces(SQLiteDatabase db) {
        ArrayList<PlaceItem> places = new ArrayList<PlaceItem>();

        int savedUserID = new UserInfo(c).getUserID();

        String[] projection = {
                placesId,
                placesTitle,
                placesLocationID,
                placesDate,
                placeImage,
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

                    long imageID = c.getLong(
                            c.getColumnIndexOrThrow(placeImage)
                    );

                    ImageItem image = getImage(imageID);

                    PlaceItem p = new PlaceItem(placeID, title, getLocation(locationID), date, image,imageID);

                    List<String> companions = getCompanionsPlaces(placeID, db);

                    List<Note> notes = getNotesPlaces(placeID, db);

                    List<ImageItem> images = getImages(db, placeID);

                    for (String comp : companions)
                        p.addCompanion(comp);

                    for (Note note : notes)
                        p.addNote(note);

                    for(ImageItem i : images)
                        p.addImage(i);


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
                placeImage,
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

        if (c.getCount() == 0) {


            return null;
        } else {

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

            long imageID = c.getLong(
                    c.getColumnIndexOrThrow(placeImage)
            );

            ImageItem image = getImage(imageID);

            PlaceItem p = new PlaceItem(placeID, title, getLocation(locationID), date, image, imageID);

            List<String> companions = getCompanionsPlaces(placeID, db);

            List<Note> notes = getNotesPlaces(placeID, db);

            List<ImageItem> images = getImages(db, placeID);

            for (String comp : companions)
                p.addCompanion(comp);

            for (Note note : notes)
                p.addNote(note);

            for(ImageItem i : images)
                p.addImage(i);

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

        String[] whereArgs = new String[]{
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

        if (c.getCount() != 0) {

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

        String[] whereArgs = new String[]{
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

        if (c.getCount() != 0) {

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

            return new GPlaces(name, vicinity, lat, lng, locationIDToGet);

        }

        return null;


    }

    public ArrayList<GPlaces> getGPlaces() {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<GPlaces> gPlaces = new ArrayList<GPlaces>();

        ArrayList<PlaceItem> places = getPlaces(db);

        for (PlaceItem p : places)
            gPlaces.add(p.getGPlace());


        return gPlaces;
    }


    public ArrayList<ImageItem> getAllImages() {

        ArrayList<ImageItem> images = new ArrayList<ImageItem>();

        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                imagesId,
                imagesSRC,
                imagesTAG,
                imagesLocationLatitude,
                imagesLocationLongitude
        };


        String whereClause = "";



        String[] whereArgs = new String[]{

        };

        String sortOrder =
                imagesId;


        Cursor c = db.query(
                imagesTable,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (c.getCount() != 0) {

            if (c.moveToFirst()) {
                do {

                    long imageID = c.getLong(
                            c.getColumnIndexOrThrow(imagesId)
                    );

                    String src = c.getString((
                            c.getColumnIndexOrThrow(imagesSRC)
                            ));

                    String tag = c.getString((
                            c.getColumnIndexOrThrow(imagesTAG)
                            ));

                    long lat = c.getLong(
                            c.getColumnIndexOrThrow(imagesLocationLatitude)
                    );

                    long lng = c.getLong(
                            c.getColumnIndexOrThrow(imagesLocationLongitude)
                    );



                    ImageItem i = new ImageItem(BitmapFactory.decodeFile(src), tag, imageID, lat, lng);

                    images.add(i);

                } while (c.moveToNext());
            }


        }

        return images;
    }

    public ImageItem getImage(long imageID) {

        ArrayList<ImageItem> images = new ArrayList<ImageItem>();

        SQLiteDatabase db = getReadableDatabase();


        String[] projection = {
                imagesId,
                imagesSRC,
                imagesTAG,
                imagesLocationLatitude,
                imagesLocationLongitude
        };


        String whereClause = "imagesID =?";



        String[] whereArgs = new String[]{
            imageID + "",
        };

        String sortOrder =
                imagesId;


        Cursor c = db.query(
                imagesTable,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (c.getCount() != 0) {

            if (c.moveToFirst()) {


                    String src = c.getString((
                            c.getColumnIndexOrThrow(imagesSRC)
                    ));

                    String tag = c.getString((
                            c.getColumnIndexOrThrow(imagesTAG)
                    ));


                long lat = c.getLong(
                        c.getColumnIndexOrThrow(imagesLocationLatitude)
                );

                long lng = c.getLong(
                        c.getColumnIndexOrThrow(imagesLocationLongitude)
                );



                return new ImageItem(BitmapFactory.decodeFile(src), tag, imageID, lat, lng);

            }


        }

        return null;
    }

    private List<ImageItem> getImages(long tripID, SQLiteDatabase db) {
        List<ImageItem> images = new ArrayList<>();


        String[] projection = {
                tripsImagesImageId,
        };


        String whereClause = tripsImagesId + " =?";



        String[] whereArgs = new String[]{
            tripID + "",
        };

        String sortOrder =
                tripsImagesTableID;


        Cursor c = db.query(
                tripsImagesTable,  // The table to query
                projection,                               // The columns to return
                whereClause,                                // The columns for the WHERE clause
                whereArgs,                            // The values for the WHERE clause
                sortOrder,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (c.getCount() != 0) {

            if (c.moveToFirst()) {
                do {

                    long imageID = c.getLong(
                            c.getColumnIndexOrThrow(tripsImagesImageId)
                    );


                    ImageItem i = getImage(imageID);

                    images.add(i);

                } while (c.moveToNext());
            }


        }

        return images;
    }

    private List<ImageItem> getImages(SQLiteDatabase db, long placeID) {
        List<ImageItem> images = new ArrayList<>();


        String[] projection = {
                placesImagesImageId,
        };


        String whereClause = placesImagesPlaceId + " =?";



        String[] whereArgs = new String[]{
                placeID + "",
        };

        String sortOrder =
                placesImagesTableID;


        Cursor c = db.query(
                placesImagesTable,  // The table to query
                projection,                               // The columns to return
                whereClause,                                // The columns for the WHERE clause
                whereArgs,                            // The values for the WHERE clause
                sortOrder,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (c.getCount() != 0) {

            if (c.moveToFirst()) {
                do {

                    long imageID = c.getLong(
                            c.getColumnIndexOrThrow(placesImagesImageId)
                    );


                    ImageItem i = getImage(imageID);

                    images.add(i);

                } while (c.moveToNext());
            }


        }

        return images;
    }



    public int update(String table, ContentValues c, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getWritableDatabase();

        return  db.update(table, c, whereClause, whereArgs);
    }


}