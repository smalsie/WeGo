package uk.ac.aston.smalljh.wego;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uk.ac.aston.smalljh.wego.R;

/**
 * Created by joshuahugh on 27/11/14.
 */
public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);





        Button signin = (Button) findViewById(R.id.login_submit_button);

        final EditText username = (EditText) findViewById(R.id.login_username);
        final EditText password = (EditText) findViewById(R.id.login_password);



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usernameInput = username.getText().toString();
                String passwordInput = password.getText().toString();

                if(checkCredentials(usernameInput, passwordInput)) {

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    // catch event that there's no activity to handle intent
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        // Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), R.string.invalid_credentials, Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private boolean checkCredentials(String username, String password) {

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
                dH.userName + " DESC";


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
            return false;
        else {

            c.moveToFirst();

            String returnedPassword = c.getString(
                    c.getColumnIndexOrThrow(dH.userPass)
            );

            return returnedPassword.equals(password);

        }


    }
}
