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

    private TextView title;

	 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {

         setRetainInstance(true);
         super.onCreate(savedInstanceState);

         View rootView = inflater.inflate(R.layout.place_overview, container, false);


         title = (TextView) rootView.findViewById(R.id.place_overview_title);

         title.setText(placeItem.getTitle() + " (" + placeItem.getGPlace().getName()+ ")");

         ImageView imageView = (ImageView) rootView.findViewById(R.id.place_overview_main_image);

         if((placeItem.getPic() != null) && (placeItem.getPic().getID() > 0))
            imageView.setImageBitmap(placeItem.getPic().getImage());

         TextView date = (TextView) rootView.findViewById(R.id.place_overview_date);

         date.setText(getResources().getText(R.string.place_date) + placeItem.getDate());

         TextView companionCount = (TextView) rootView.findViewById(R.id.place_overview_companion_count);

         String companionStr = "";

         int companionCountNum = placeItem.getCompanions().size();

         if(companionCountNum == 0)
             companionStr = "With no other companions.";
         else if(companionCountNum == 1)
             companionStr = "With one companion.";
         else if(companionCountNum > 1)
             companionStr = "With " + companionCountNum + " companions.";

         companionCount.setText(companionStr);

         TextView notesCount = (TextView) rootView.findViewById(R.id.place_overview_note_count);

         String notesStr = "";

         int notesCountNum = placeItem.getNotes().size();

         if(notesCountNum == 0)
             notesStr = "And no other notes.";
         else if(notesCountNum == 1)
             notesStr = "And one note.";
         else if(notesCountNum > 1)
             notesStr = "And " + notesCountNum + " notes.";

         notesCount.setText(notesStr);


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
            //fm.beginTransaction().replace(R.id.place_overview_map, fragment).commit();
            //fragment.getMapAsync(this);
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        refresh();
           /* map = fragment.getMap();

            GPlaces gPlaces = tripItem.getGPlace();

            LatLng latlng = new LatLng(gPlaces.getLatitude(), gPlaces.getLongitude());

            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setZoomGesturesEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));

            map.addMarker(new MarkerOptions()
                    .title(gPlaces.getName())
                    .snippet(gPlaces.getVicinity())
                    .position(latlng)
                    .visible(true));

                    */

    }

    @Override
    public void refresh() {
        title.setText(placeItem.getTitle() + " (" + placeItem.getGPlace().getName()+ ")");
    }
}




