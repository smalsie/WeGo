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
public class AddTripActivity extends AddObject implements DatePickerClass.DatePickerReturn {

    private String startDate = "";
    private String endDate = "";

    private int returnCode = 100;

    private Button startDateBtn, endDateBtn;

    private TripItem tripItem;

    private boolean startDateSet = false;
    private boolean endDateSet = false;


    private final int START_DATE_REQUEST_CODE = 4;
    private final int END_DATE_REQUEST_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_trip);

        isPlace = false;

        nameEditText = (EditText) findViewById(R.id.add_trip_name);
        locationEditText = (EditText) findViewById(R.id.add_trip_location);

        startDateBtn = (Button) findViewById(R.id.add_trip_start_date);
        startDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v, START_DATE_REQUEST_CODE);
            }
        });

        endDateBtn = (Button) findViewById(R.id.add_trip_end_date);
        endDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v, END_DATE_REQUEST_CODE);
            }
        });

        locationEditText = (EditText)
                findViewById(R.id.add_trip_location);

        image = (ImageView) findViewById(R.id.add_trip_image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getImage();
            }
        });

        ImageButton locationButton = (ImageButton) findViewById(R.id.add_trip_locate_button);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapPane.class);
                startActivityForResult(intent, LOCATION_RESULT_CODE);

            }
        });

        submit = (Button) findViewById(R.id.add_trip_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (submit()) {

                    Toast.makeText(getApplicationContext(), getText(R.string.trip_added), Toast.LENGTH_LONG).show();

                    if (editing) {
                        Intent resultIntent = new Intent();
                        setResult(Activity.RESULT_OK, resultIntent);
                    }

                    finish();

                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setElevation(0);

        getSupportActionBar().setTitle("Add New Trip");

        long tripID = getIntent().getLongExtra("TripID", 0);

        if (tripID != 0)
            edit(tripID);

    }

    public void showTimePickerDialog(View v, int returnCode) {
        DialogFragment newFragment = new DatePickerClass();

        this.returnCode = returnCode;

        Bundle mBundle = new Bundle();

        if (!startDate.equals("")) {

            int[] dateArr = getDayMonthYear(startDate);


            mBundle.putInt("day", dateArr[0]);
            mBundle.putInt("month", dateArr[1]);
            mBundle.putInt("year", dateArr[2]);

            newFragment.setArguments(mBundle);
        }

        mBundle.putInt("returnCode", returnCode);


        newFragment.show(getFragmentManager(), "timePicker");
    }

    @Override
    public void onFinishEditDialog(int year, int month, int day) {

        if (returnCode == START_DATE_REQUEST_CODE) {

            startDate = niceDate(year, month, day);

            startDateBtn.setText(startDate);

            startDateSet = true;

        } else if (returnCode == END_DATE_REQUEST_CODE) {

            endDate = niceDate(year, month, day);

            endDateBtn.setText(endDate);

            endDateSet = true;

        }


    }

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
        } else if(!startDateSet) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please Select A Start Date!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

            return false;

        }  else if(!endDateSet) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please Select An End Date!")
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
        } else {

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

                if (editing)
                    imageID = tripItem.getImageID();

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

            c.put(dh.tripsUserId, ui.getUserID());
            c.put(dh.tripsTitle, name);
            c.put(dh.tripsLocationID, locationInsertID);
            c.put(dh.tripsStartDate, startDate);
            c.put(dh.tripsEndDate, endDate);
            c.put(dh.tripImage, imageID);

            long id = 0;

            if (editing) {

                String whereClause = dh.tripsId + "=?";

                String[] whereArgs = new String[]{
                        tripItem.getID() + "",
                };

                int affected = dh.update(dh.tripsTable, c, whereClause, whereArgs);

                Log.i("Edit Place Place", affected + "");

            } else {

                id = dh.insert(dh.tripsTable, c);

            }


        }

        return true;
    }

    private void edit(long tripID) {

        endDateSet = startDateSet = true;

        getSupportActionBar().setTitle("Edit Trip");

        editing = true;

        DatabaseHelper dh = new DatabaseHelper(getApplicationContext());

        tripItem = dh.getTrip(dh.getWritableDatabase(), tripID);

        place = tripItem.getGPlace();

        locID = place.getLocID();



        nameEditText.setText(tripItem.getTitle());

        startDate = tripItem.getStartDate();
        endDate = tripItem.getEndDate();

        startDateBtn.setText(startDate);
        endDateBtn.setText(endDate);
        locationEditText.setText(place.getName());


        if ((tripItem.getPic() != null) && tripItem.getPic().getID() > 0) {
            image.setImageBitmap(tripItem.getPic().getImage());

            imageLat = tripItem.getPic().getLatitude();
            imageLng = tripItem.getPic().getLongitude();
            //setPic();
        }

    }

}
