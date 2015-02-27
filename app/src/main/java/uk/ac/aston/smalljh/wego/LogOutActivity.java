package uk.ac.aston.smalljh.wego;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by joshuahugh on 27/02/15.
 */
public class LogOutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout);

        try {

            new Thread() {
                public void run() {
                    try {
                        // sleep the thread, whatever time you want.
                        sleep(3000);

                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    } catch (Exception e) {
                    }
                    // progDailog.dismiss();
                }
            }.start();



        } catch (Exception e) {
            Log.e("Spinner", e.getMessage());
            //PopIt("CheckAccountError", e.getMessage(), "Denied");
        }



    }

    @Override
    public void onBackPressed() {
    }

}
