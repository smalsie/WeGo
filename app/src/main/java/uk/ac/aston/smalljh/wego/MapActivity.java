package uk.ac.aston.smalljh.wego;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aston.smalljh.wego.utils.DatabaseHelper;
import uk.ac.aston.smalljh.wego.utils.DatePickerClass;
import uk.ac.aston.smalljh.wego.utils.GPlaces;

public class MapActivity extends ActionBarActivity implements
        OnMapReadyCallback, DatePickerClass.DatePickerReturn {

    private final int PLACE_MAP = 1;

    private final int TRIP_MAP = 2;

    private final int ALL_MAP = 3;

    private int returnCode = 100;

    private String startDate = "";
    private String endDate = "";

    private Button startDateBTN, endDateBtn, resetBtn;

    private final int START_DATE_REQUEST_CODE = 4;
    private final int END_DATE_REQUEST_CODE = 5;

    private int type = 3;

    private long tripID, placeID;

    private ArrayList<PlaceItem> placeItems;

    private DatabaseHelper dh;

    private List<Marker> markerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        tripID = getIntent().getLongExtra("TRIP", 0);

        placeID = getIntent().getLongExtra("PLACE", 0);

        if(tripID > 0)
            type = TRIP_MAP;
        else if(placeID > 0)
            type = PLACE_MAP;


        startDateBTN = (Button) findViewById(R.id.filter_start_date);
        startDateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v, START_DATE_REQUEST_CODE);
            }
        });

        endDateBtn = (Button) findViewById(R.id.filter_end_date);
        endDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v, END_DATE_REQUEST_CODE);
            }
        });

        resetBtn = (Button) findViewById(R.id.filter_reset);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map_activity_map);
        mapFragment.getMapAsync(this);

        dh = new DatabaseHelper(getApplicationContext());

        placeItems = dh.getPlaces(dh.getReadableDatabase());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setElevation(0);

        getSupportActionBar().setTitle("Your Map");

        resetBtn.setVisibility(View.GONE);

        if(type != ALL_MAP) {
            startDateBTN.setVisibility(View.GONE);
            endDateBtn.setVisibility(View.GONE);
        }

    }


    @Override
    public void onMapReady(GoogleMap map) {


        map.setMyLocationEnabled(true);

        if(type == TRIP_MAP) {

            TripItem tripItem = dh.getTrip(dh.getReadableDatabase(), tripID);

            addPoint(map, tripItem);

        } else if(type == PLACE_MAP) {

            PlaceItem placeItem = dh.getPlace(placeID, dh.getReadableDatabase());

            addPoint(map, placeItem);

        } else {

            for(PlaceItem p : placeItems) {
                addPoint(map, p);
            }

        }



    }

    private void addPoint(GoogleMap map, final PlaceItem placeItem) {

        GPlaces gPlace = placeItem.getGPlace();

        LatLng latlng = new LatLng(gPlace.getLatitude(), gPlace.getLongitude());

        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 2));

        markerList.add(map.addMarker(new MarkerOptions()
                .title(placeItem.getTitle() + " (" + gPlace.getName() + ")")
                .snippet(gPlace.getVicinity())
                .position(latlng)
                .visible(true)));

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                int position = 0;
                for (int i = 0; i < markerList.size(); i++) {
                    if (markerList.get(i).equals(marker)) {
                        position = i;

                        Intent intent = new Intent(getApplicationContext(), PlaceViewActivity.class);

                        intent.putExtra("Place", placeItems.get(position).getID());

                        startActivity(intent);

                        break;
                    }
                }

            }
        });





    }

    private void addPoint(GoogleMap map, final TripItem tripItem) {

        GPlaces gPlace = tripItem.getGPlace();

        LatLng latlng = new LatLng(gPlace.getLatitude(), gPlace.getLongitude());

        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 2));

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getApplicationContext(), TripViewActivity.class);

                intent.putExtra("TRIP", tripItem.getID());

                startActivity(intent);
            }
        });

        map.addMarker(new MarkerOptions()
                .title(tripItem.getTitle() + " (" + gPlace.getName() + ")")
                .snippet(gPlace.getVicinity())
                .position(latlng)
                .visible(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        List<PlaceItem> places = tripItem.getPlaces();

        for(PlaceItem p : places)
            addPoint(map, p);



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

        resetBtn.setVisibility(View.VISIBLE);

        if (returnCode == START_DATE_REQUEST_CODE) {

            startDate = niceDate(year, month, day);

            startDateBTN.setText(startDate);



        } else if (returnCode == END_DATE_REQUEST_CODE) {

            endDate = niceDate(year, month, day);

            endDateBtn.setText(endDate);

        }


    }

    protected String niceDate(int year, int month, int day) {

        month++;

        String dayString = "" + day;
        String monthString = "" + month;

        if (day < 10)
            dayString = "0" + day;

        if (month < 10)
            monthString = "0" + month;

        return dayString + "/" + monthString + "/" + year;
    }


    protected int[] getDayMonthYear(String date) {

        String[] dateStr = date.split("/");

        int[] dateArr = new int[3];

        for (int i = 0; i < 3; i++)
            dateArr[i] = Integer.parseInt(dateStr[i]);


        return dateArr;


    }

    private void reset() {
        startDate = endDate = "";
        startDateBTN.setText("Start Date");
        endDateBtn.setText("End Date");

        resetBtn.setVisibility(View.GONE);
    }


}