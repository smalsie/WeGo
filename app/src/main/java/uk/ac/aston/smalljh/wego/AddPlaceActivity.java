package uk.ac.aston.smalljh.wego;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import uk.ac.aston.smalljh.wego.fragments.UserInfo;
import uk.ac.aston.smalljh.wego.utils.DatabaseHelper;
import uk.ac.aston.smalljh.wego.utils.DatePickerClass;
import uk.ac.aston.smalljh.wego.utils.GPlaces;

/**
 * Created by joshuahugh on 27/02/15.
 */
public class AddPlaceActivity extends AddObject implements DatePickerClass.DatePickerReturn {

    private String date;
    protected boolean dateSet = false;

    private PlaceItem placeItem;

    private Button dateButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_place);


        dh = new DatabaseHelper(getApplicationContext());

        locationEditText = (EditText)
                findViewById(R.id.add_place_location);

        image = (ImageView) findViewById(R.id.add_place_image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getImage();
            }
        });

        ImageButton locationButton = (ImageButton) findViewById(R.id.add_place_locate_button);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapPane.class);
                startActivityForResult(intent, LOCATION_RESULT_CODE);

            }
        });

        dateButton = (Button) findViewById(R.id.add_place_date);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });

        /*final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        String date = niceDate(year, month, day);

        dateButton.setText(date);*/

        submit = (Button) findViewById(R.id.add_place_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (submit()) {

                    Toast.makeText(getApplicationContext(), getText(R.string.place_added), Toast.LENGTH_LONG).show();

                    if (editing) {
                        Intent resultIntent = new Intent();
                        setResult(Activity.RESULT_OK, resultIntent);
                    }

                    finish();

                }
            }
        });



        nameEditText = (EditText) findViewById(R.id.add_place_name);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setElevation(0);


        getSupportActionBar().setTitle("Add New Place");

        long placeID = getIntent().getLongExtra("PlaceID", 0);

        if (placeID != 0)
            edit(placeID);


    }





    @Override
    public void onFinishEditDialog(int year, int month, int day) {

        dateSet = true;

        date = niceDate(year, month, day);

        dateButton.setText(date);
    }


    @Override
    protected boolean submit() {
        String name = nameEditText.getText().toString();

        if (name.equals("")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please Enter in a Name!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

            return false;
        } else if(!dateSet) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please Select A Date!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

            return false;

        } else if(locationEditText.getText().toString().equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please Select A Location!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

            return false;
        } else
         {

            ContentValues c = new ContentValues();


            DatabaseHelper dh = new DatabaseHelper(getApplicationContext());

            if (place == null) {
                place = new GPlaces(locationEditText.getText().toString(), "", null, null, 0);
            }


            c.put(dh.LocationLatitude, place.getLatitude());
            c.put(dh.LocationLongitude, place.getLongitude());
            c.put(dh.LocationName, place.getName());
            c.put(dh.LocationVicinity, place.getVicinity());

            long locationInsertID;

            if (editing) {

                locationInsertID = locID;

                String whereClause = dh.locationID + "=?";

                String[] whereArgs = new String[]{
                        locID + "",
                };

                int affected = dh.update(dh.locationTable, c, whereClause, whereArgs);

                Log.i("Edit Place", affected + "");


            } else {

                locationInsertID = dh.insert(dh.locationTable, c);

            }


            UserInfo ui = new UserInfo(getApplicationContext());

            long imageID = 0;

            if (!mCurrentPhotoPath.equals("")) {

                c = new ContentValues();

                c.put(dh.imagesUserId, ui.getUserID());
                c.put(dh.imagesSRC, mCurrentPhotoPath);
                c.put(dh.imagesTAG, tag);
                c.put(dh.imagesLocationLatitude, imageLat);
                c.put(dh.imagesLocationLongitude, imageLng);

                if(editing)
                    imageID = placeItem.getImageID();

                if (editing && (imageID > 0)) {

                    String whereClause = dh.imagesId + "=?";

                    String[] whereArgs = new String[]{
                            imageID + "",
                    };

                    int affected = dh.update(dh.imagesTable, c, whereClause, whereArgs);

                    Log.i("Edit Place Image", affected + "");
                } else {


                    imageID = dh.insert(dh.imagesTable, c);

                }

            }

            c = new ContentValues();

            c.put(dh.placesUserId, ui.getUserID());
            c.put(dh.placesTitle, name);
            c.put(dh.placesLocationID, locationInsertID);
            c.put(dh.placesDate, dateButton.getText().toString());
            c.put(dh.placeImage, imageID);

            long id = 0;

            if (editing) {

                String whereClause = dh.placesId + "=?";

                String[] whereArgs = new String[]{
                        placeItem.getID() + "",
                };

                int affected = dh.update(dh.placesTable, c, whereClause, whereArgs);

                Log.i("Edit Place Place", affected + "");

            } else {

                id = dh.insert(dh.placesTable, c);

            }




        }

        return true;


    }

    private void edit(long placeID) {

        editing = true;

        dateSet = true;


        getSupportActionBar().setTitle("Edit Place");

        DatabaseHelper dh = new DatabaseHelper(getApplicationContext());

        placeItem = dh.getPlace(placeID, dh.getReadableDatabase());

        place = placeItem.getGPlace();

        locID = place.getLocID();



        nameEditText.setText(placeItem.getTitle());
        dateButton.setText(placeItem.getDate());
        locationEditText.setText(place.getName());


        if ((placeItem.getPic()!= null) && (placeItem.getPic().getID() > 0)) {
           image.setImageBitmap(placeItem.getPic().getImage());


            imageLat = placeItem.getPic().getLatitude();
            imageLng = placeItem.getPic().getLongitude();
        }


    }


    private void showTimePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerClass();

        String date = dateButton.getText().toString();

        if (!date.equals("")) {

            int[] dateArr = getDayMonthYear(date);

            Bundle mBundle = new Bundle();
            mBundle.putInt("day", dateArr[0]);
            mBundle.putInt("month", dateArr[1]);
            mBundle.putInt("year", dateArr[2]);

            newFragment.setArguments(mBundle);
        }


        newFragment.show(getFragmentManager(), "timePicker");
    }



}