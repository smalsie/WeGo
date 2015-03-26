package uk.ac.aston.smalljh.wego.fragments.trips;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import uk.ac.aston.smalljh.wego.R;

public class TripsOverviewFragment extends TripsFrag {

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

         title.setText(tripItem.getTitle() + " (" + tripItem.getGPlace().getName()+ ")");

         ImageView imageView = (ImageView) rootView.findViewById(R.id.place_overview_main_image);

         if((tripItem.getPic() != null) && (tripItem.getPic().getID() > 0))
             imageView.setImageBitmap(tripItem.getPic().getImage());

         TextView date = (TextView) rootView.findViewById(R.id.place_overview_date);

         date.setText("You were here from " + tripItem.getStartDate() + " to " + tripItem.getEndDate());

         TextView companionCount = (TextView) rootView.findViewById(R.id.place_overview_companion_count);

         String companionStr = "";

         int companionCountNum = tripItem.getCompanions().size();

         if(companionCountNum == 0)
             companionStr = "With no companions.";
         else if(companionCountNum == 1)
             companionStr = "With one companion.";
         else if(companionCountNum > 1)
             companionStr = "With " + companionCountNum + " companions.";

         companionCount.setText(companionStr);

         TextView notesCount = (TextView) rootView.findViewById(R.id.place_overview_note_count);

         String notesStr = "";

         int notesCountNum = tripItem.getNotes().size();

         if(notesCountNum == 0)
             notesStr = "And no notes.";
         else if(notesCountNum == 1)
             notesStr = "And one note.";
         else if(notesCountNum > 1)
             notesStr = "And " + notesCountNum + " notes.";

         notesCount.setText(notesStr);

         TextView placesCount = (TextView) rootView.findViewById(R.id.place_overview_places_count);
         placesCount.setVisibility(View.VISIBLE);

         String placesStr = "";

         int placesCountNum = tripItem.getPlaces().size();

         if(placesCountNum == 0)
             placesStr = "You visited no places.";
         else if(placesCountNum == 1)
             placesStr = "You visited one place.";
         else if(placesCountNum > 1)
             placesStr = "You visited " + placesCountNum + " places.";

         placesCount.setText(placesStr);

         TextView galleryCount = (TextView) rootView.findViewById(R.id.place_overview_picture_count);
         galleryCount.setVisibility(View.VISIBLE);

         String galleryStr = "";

         int galleryCountNum = tripItem.getNotes().size();

         if(galleryCountNum == 0)
             galleryStr = "You took no photos.";
         else if(galleryCountNum == 1)
             galleryStr = "You took one photo.";
         else if(galleryCountNum > 1)
             galleryStr = "You took " + galleryCountNum + " photos.";

         galleryCount.setText(galleryStr);



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

        title.setText(tripItem.getTitle() + " (" + tripItem.getGPlace().getName()+ ")");
    }
}




