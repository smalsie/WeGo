package uk.ac.aston.smalljh.wego;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import uk.ac.aston.smalljh.wego.utils.GPlaces;
import uk.ac.aston.smalljh.wego.utils.GooglePlacesUtils;

public class MapPane extends Activity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {

    private boolean fullscreen;
    private Activity activity;
    protected GoogleApiClient mGoogleApiClient;

    protected static final String TAG = "basic-location-sample";

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);



        activity = this;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        fullscreen = false;

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final LinearLayout content = (LinearLayout) findViewById(R.id.locations);

        final ImageButton fullscreenButton = (ImageButton) findViewById(R.id.map_full_screen_button);
        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fullscreen) {

                    fullscreen = false;
                    content.setVisibility(View.VISIBLE);
                    fullscreenButton.setImageResource(R.drawable.ic_action_full_screen);

                } else {

                    fullscreen = true;
                    content.setVisibility(View.GONE);
                    fullscreenButton.setImageResource(R.drawable.ic_action_return_from_full_screen);

                }

            }
        });

        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();

        buildGoogleApiClient();

    }


    @Override
    public void onMapReady(GoogleMap map) {



        map.setMyLocationEnabled(true);




    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {



            String key = "AIzaSyDk1f-jCsuh0L5UKe68iTfVhhTC6cIQ6gE";
            String distance = "1000";
            String latlng = mLastLocation.getLatitude() + "," + mLastLocation.getLongitude();

            String placesRequest = "https://maps.googleapis.com/maps/api/place/search/json"
                    + "?location=" + latlng
                    + "&radius=" + distance
                    + "&key=" + key;
            PlacesFeed detailTask = new PlacesFeed();
            detailTask.execute(placesRequest);
        } else {
            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    private class PlacesFeed extends AsyncTask<String, Void, ArrayList<GPlaces>> {

        ArrayList<GPlaces> foundPlaces;

        @Override
        protected ArrayList<GPlaces> doInBackground(String... urls) {
            JSONObject predictions;
            try {
                String input = GooglePlacesUtils.readGooglePlaces(urls[0]);

                JSONObject object = new JSONObject(input);
                JSONArray array = object.getJSONArray("results");

                foundPlaces = new ArrayList<GPlaces>();
                for (int i = 0; i < array.length(); i++) {
                    try {
                        GPlaces place = GPlaces
                                .jsonToPontoReferencia((JSONObject) array.get(i));
                        Log.v("Places Services ", "" + place);
                        foundPlaces.add(place);
                    } catch (Exception e) {
                    }
                }
                return foundPlaces;
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            return null;


        }


        @Override
        protected void onPostExecute(
                ArrayList<GPlaces> placeDetail) {
            //place = placeDetail.getResult();
            // fill in the layout with the data
            Log.i("Places", placeDetail.size() + "");

            ArrayList<GPlaces> places = new ArrayList<GPlaces>();

            for(int i = 0; (i < foundPlaces.size() && i < 10); i++)
                places.add(foundPlaces.get(i));

            final ListView listView = (ListView) findViewById(R.id.listview_for_places);

            final PlacesArrayAdaptor arrayAdapter = new PlacesArrayAdaptor(activity, places);

            listView.setAdapter(arrayAdapter);
        }
    }


    private class PlacesArrayAdaptor extends ArrayAdapter<GPlaces> {
        private final Context context;
        private final ArrayList<GPlaces> places;

        public PlacesArrayAdaptor(Context context,ArrayList<GPlaces> places) {

            super(context, R.layout.map,places);
            this.context = context;
            this.places = places;
        }

        /**
         * Overide the view method
         *
         * @return a view (the list of all of the events)
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //get the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate events_list.xml
            View rowView = inflater.inflate(R.layout.google_place_item, parent, false);

            //Each of the textviews to add specified content to
            TextView title= (TextView) rowView.findViewById(R.id.google_places_name);
            TextView address= (TextView) rowView.findViewById(R.id.google_places_address);

            title.setText(places.get(position).getName());
            address.setText(places.get(position).getVicinity());

            return rowView;
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

}