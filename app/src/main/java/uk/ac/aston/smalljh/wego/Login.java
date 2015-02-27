package uk.ac.aston.smalljh.wego;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import uk.ac.aston.smalljh.wego.R;
import uk.ac.aston.smalljh.wego.fragments.UserInfo;

/**
 * Created by joshuahugh on 27/11/14.
 */
public class Login extends Activity {

    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userInfo = new UserInfo(getApplicationContext());

        int id = userInfo.getUserID();

        if (id > 0)
            loggedIn(id);
        else {

            LinearLayout loginForm = (LinearLayout) findViewById(R.id.login_form);
            loginForm.setVisibility(View.VISIBLE);


            Button signin = (Button) findViewById(R.id.login_submit_button);

            final EditText username = (EditText) findViewById(R.id.login_username);
            final EditText password = (EditText) findViewById(R.id.login_password);


            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String usernameInput = username.getText().toString();
                    String passwordInput = password.getText().toString();

                    int userId = checkCredentials(usernameInput, passwordInput);

                    if (userId >0) {

                        loggedIn(userId);

                    } else {
                        Toast.makeText(getApplicationContext(), R.string.invalid_credentials, Toast.LENGTH_LONG).show();
                    }
                }
            });

            Button signUp = (Button) findViewById(R.id.login_sign_up_button);

            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                        Log.i("SIGN UP", "Sign Up Pressed");
                        loggedIn(0);


                }
            });

        }
    }

    private int checkCredentials(String username, String password) {

        DatabaseHelper dH = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dH.getReadableDatabase();

        String[] projection = {
                dH.userID,
                dH.userPass,
        };

        String whereClause = dH.userName + "=?";

        String[] whereArgs = new String[] {
                username,
        };

        String sortOrder =
                dH.userName;


        Cursor c = db.query(
                dH.userTable,  // The table to query
                projection,                               // The columns to return
                whereClause,                                // The columns for the WHERE clause
                whereArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if(c.getCount() == 0)
            return 0;
        else {

            c.moveToFirst();

            String returnedPassword = c.getString(
                    c.getColumnIndexOrThrow(dH.userPass)
            );
            int returnedId = c.getInt(
                    c.getColumnIndexOrThrow(dH.userID)
            );

           if(returnedPassword.equals(password))
               return returnedId;
            else
               return 0;

        }


    }
    @Override
    protected void onResume(){
        super.onResume();

       // finish();
        //startActivity(getIntent());
    }

    private void loggedIn(int userId) {


        userInfo.setUserID(userId);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        // catch event that there's no activity to handle intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
        }
    }
}
