package uk.ac.aston.smalljh.wego.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import uk.ac.aston.smalljh.wego.NearbyPlaceItem;
import uk.ac.aston.smalljh.wego.PlaceItem;
import uk.ac.aston.smalljh.wego.R;

public class NearbyPlacesFragment extends Fragment {
	
	 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.places_main, container, false);


         ArrayList<NearbyPlaceItem> nearbyPlaceItems = new ArrayList<NearbyPlaceItem>();

         nearbyPlaceItems.add(new NearbyPlaceItem("Brooklyn", "3 Miles Away!", 5, R.drawable.brooklyn));
         nearbyPlaceItems.add(new NearbyPlaceItem("Brooklyn", "3 Miles Away!", 5, R.drawable.brooklyn));


         final ListView listView = (ListView) rootView.findViewById(R.id.listview);

         final ContactArrayAdaptor arrayAdapter = new ContactArrayAdaptor(getActivity(), nearbyPlaceItems);

         listView.setAdapter(arrayAdapter);


         getActivity().setTitle(R.string.nearby_places);
         return rootView;
     }




    private class ContactArrayAdaptor extends ArrayAdapter<NearbyPlaceItem> {
        private final Context context;
        private final ArrayList<NearbyPlaceItem> nearbyPlaceItems;

        public ContactArrayAdaptor(Context context,ArrayList<NearbyPlaceItem> nearbyPlaceItems) {

            super(context, R.layout.home_fragment,nearbyPlaceItems);
            this.context = context;
            this.nearbyPlaceItems = nearbyPlaceItems;
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
            View rowView = inflater.inflate(R.layout.nearby_places_item, parent, false);

            //Each of the textviews to add specified content to
            TextView place= (TextView) rowView.findViewById(R.id.nearby_place_location);
            TextView distance= (TextView) rowView.findViewById(R.id.nearby_place_distance_away);
            TextView rating= (TextView) rowView.findViewById(R.id.nearby_place_rating);
            ImageView image = (ImageView) rowView.findViewById(R.id.nearby_place_icon);

            place.setText(nearbyPlaceItems.get(position).getPlace());

            int pic = nearbyPlaceItems.get(position).getPic();

            distance.setText(nearbyPlaceItems.get(position).getDistance());

            rating.setText(nearbyPlaceItems.get(position).getStars());

            image.setImageResource(pic);


            return rowView;
        }
    }
}




