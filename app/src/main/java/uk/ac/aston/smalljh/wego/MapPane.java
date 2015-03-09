package uk.ac.aston.smalljh.wego;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class MapPane extends Activity implements OnMapReadyCallback {

    private boolean fullscreen;
    private Activity activity;

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

            title.setText(places.get(position).getName());

            return rowView;
        }
    }
}