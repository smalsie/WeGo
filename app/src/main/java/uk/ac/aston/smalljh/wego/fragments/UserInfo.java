package uk.ac.aston.smalljh.wego.fragments;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by joshuahugh on 27/02/15.
 */
public class UserInfo {
    public static final String PREFS_NAME = "WeGoUserID";

    private Context context;

    public UserInfo(Context context) { this.context = context;}

    public int getUserID() {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        return settings.getInt(PREFS_NAME, 0);
    }

    public void setUserID(int userId) {

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(PREFS_NAME, userId);

        editor.commit();
    }


}
