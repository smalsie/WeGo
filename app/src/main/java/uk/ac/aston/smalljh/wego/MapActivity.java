package uk.ac.aston.smalljh.wego;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import uk.ac.aston.smalljh.wego.utils.GPlaces;
import uk.ac.aston.smalljh.wego.utils.GooglePlacesUtils;

public class MapActivity extends Activity implements
        OnMapReadyCallback {

    private ArrayList<PlaceItem> placeItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map_activity_map);
        mapFragment.getMapAsync(this);

        DatabaseHelper dh = new DatabaseHelper(getApplicationContext());

        placeItems = dh.getPlaces(dh.getReadableDatabase());

    }


    @Override
    public void onMapReady(GoogleMap map) {


        map.setMyLocationEnabled(true);

        for(PlaceItem p : placeItems) {
            addPoint(map, p);
        }


    }

    private void addPoint(GoogleMap map, PlaceItem placeItem) {

        GPlaces gPlace = placeItem.getGPlace();

        LatLng latlng = new LatLng(gPlace.getLatitude(), gPlace.getLongitude());

        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 2));

        map.addMarker(new MarkerOptions()
                .title(gPlace.getName())
                .snippet(gPlace.getVicinity())
                .position(latlng)
                .visible(true));

    }


}