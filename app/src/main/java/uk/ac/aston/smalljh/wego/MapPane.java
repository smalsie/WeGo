package uk.ac.aston.smalljh.wego;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import uk.ac.aston.smalljh.wego.R;
import uk.ac.aston.smalljh.wego.utils.GooglePlace;
import uk.ac.aston.smalljh.wego.utils.GooglePlacesUtils;
import uk.ac.aston.smalljh.wego.utils.PlaceDetail;

public class MapPane extends Activity implements OnMapReadyCallback {

    private boolean fullscreen;
    private GooglePlace place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

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



    }


    @Override
    public void onMapReady(GoogleMap map) {



        LatLng sydney = new LatLng(-33.867, 151.206);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(sydney));

        map.setMyLocationEnabled(true);

        String key = "AIzaSyDk1f-jCsuh0L5UKe68iTfVhhTC6cIQ6gE";
        String distance = "10";
        String latlng = "37.787930,-122.4074990";

        String placesRequest = "https://maps.googleapis.com/maps/api/place/search/json"
                + "?location=" + latlng
                + "&radius=" + distance
                + "&key=" + key;
        PlacesFeed detailTask = new PlacesFeed();
        detailTask.execute(placesRequest);

    }

    private class PlacesFeed extends AsyncTask<String, Void, PlaceDetail> {
        @Override
        protected PlaceDetail doInBackground(String... urls) {
            JSONObject predictions;
            try {
                String input = GooglePlacesUtils.readGooglePlaces(urls[0]);
                predictions = new JSONObject(input);
                JSONArray ja = (JSONArray) predictions.get("results");

                Gson gson = new Gson();

                String result = ja.toString();
                result = result.substring(1, result.length() - 1);
                Log.i("Places", result);
                Type collectionType = new TypeToken<ArrayList<PlacesFeed>>(){}.getType();
                ArrayList<PlaceDetail> places = gson.fromJson(result, collectionType);

                for(PlaceDetail p : places) {
                    Log.i("Places", p.toString());
                }

                return null;
            } catch (Exception e) {

                e.printStackTrace();
                Log.i("Places", e.getMessage());
                return null;

            }

        }

        @Override
        protected void onPostExecute(
                PlaceDetail placeDetail) {
            //place = placeDetail.getResult();
            // fill in the layout with the data
        }
    }
}