package uk.ac.aston.smalljh.wego.fragments.places;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import uk.ac.aston.smalljh.wego.PlaceItem;
import uk.ac.aston.smalljh.wego.R;
import uk.ac.aston.smalljh.wego.utils.GPlaces;

public class PlacesOverviewFragment extends PlacesFrag {

    private SupportMapFragment fragment;
    private GoogleMap map;

	 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {

         setRetainInstance(true);
         super.onCreate(savedInstanceState);

         View rootView = inflater.inflate(R.layout.place_overview, container, false);


         TextView title = (TextView) rootView.findViewById(R.id.place_overview_title);

         title.setText(placeItem.getTitle());


         return rootView;
     }

    @Override
    public String getName() {
        return "Overview";
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.place_overview_map, fragment).commit();
            //fragment.getMapAsync(this);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
            map = fragment.getMap();

            GPlaces gPlaces = placeItem.getGPlace();

            LatLng latlng = new LatLng(gPlaces.getLatitude(), gPlaces.getLongitude());

            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setZoomGesturesEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));

            map.addMarker(new MarkerOptions()
                    .title(gPlaces.getName())
                    .snippet(gPlaces.getVicinity())
                    .position(latlng)
                    .visible(true));

    }
}




