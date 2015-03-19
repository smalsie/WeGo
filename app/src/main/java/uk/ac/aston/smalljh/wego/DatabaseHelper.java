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
    public static final String tripsNotes = "TripNotes";

    //TripImages
    public static final String tripsImagesTable = "TripImages";
    public static final String tripsImagesId = "TripID";
    public static final String tripsImagesImageId = "ImageID";

    //TripCompanions
    public static final String tripsCompanionsTable = "TripCompanions";
    public static final String tripsCompanionsId = "TripID";
    public static final String tripsCompanionsUserId = "UserID";

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
    public static final String placesLocation = "PlaceLocation";
    public static final String placesStartDate = "PlaceStartDate";
    public static final String placesEndDate = "PlaceEndDate";
    public static final String placesNotes = "PlaceNotes";

    //PlaceImages
    public static final String placesImagesTable = "PlacesImages";
    public static final String placesImagesPlaceId = "PlaceID";
    public static final String placesImagesImageId = "ImageID";

    //PlaceCompanions
    public static final String placesCompanionsTable = "PlacesCompanions";
    public static final String placesCompanionsPlaceId = "PlaceID";
    public static final String placesCompanionsCompanionId = "ImageID";

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
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
                + tripsEndDate + " TEXT, "
                + tripsNotes + " TEXT)";
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
                + tripsCompanionsUserId + " INTEGER)";
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
                + placesLocation + " TEXT, "
                + placesStartDate + " TEXT, "
                + placesEndDate + " TEXT, "
                + placesNotes + " TEXT)";
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
                + placesCompanionsCompanionId + " INTEGER)";
        Log.i("DBHELPER", sql);
        db.execSQL(sql);

        insertInitialFeeds(db, "smalsie", "1234");
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


    public void insert(String table, Map<String, Object> inserts) {

        SQLiteDatabase db = getReadableDatabase();

        String sql = "INSERT INTO " + table + " (";

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

        db.execSQL(sql);
    }

}