package uk.ac.aston.smalljh.wego;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import uk.ac.aston.smalljh.wego.utils.GPlaces;

/**
 * Created by joshuahugh on 27/02/15.
 */
public class AddPlaceActivity extends Activity {

    private static final int LOCATION_RESULT_CODE = 1;

    private AutoCompleteTextView location;

    private GPlaces place;

    private static final String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain", "Spainn"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_place);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.location_auto_complete);
        textView.setAdapter(adapter);


        ImageButton locationButton = (ImageButton) findViewById(R.id.add_place_locate_button);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapPane.class);
                startActivityForResult(intent, LOCATION_RESULT_CODE);
            }
        });

        location = (AutoCompleteTextView) findViewById(R.id.location_auto_complete);



    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle(R.string.quit_add_place_alert_title)
                .setMessage(R.string.quit_add_place_alert_message)
                .setPositiveButton(R.string.quit_add_place_alert_title, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (LOCATION_RESULT_CODE) : {
                if (resultCode == Activity.RESULT_OK) {

                    place = (GPlaces) data.getParcelableExtra("Place");

                    location.setText(place.getVicinity());

                }
                break;
            }
        }
    }

}
