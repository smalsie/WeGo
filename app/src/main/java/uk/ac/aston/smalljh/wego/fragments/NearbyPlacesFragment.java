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
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirections;

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

         nearbyPlaceItems.add(new NearbyPlaceItem("Aston University", "0.2 Miles Away!", 5, R.drawable.aston));
         nearbyPlaceItems.add(new NearbyPlaceItem("BCU", "0.5 Miles Away!", 2, R.drawable.bcu));
         nearbyPlaceItems.add(new NearbyPlaceItem("Millennium Point", "1 Mile Away!", 4, R.drawable.mil));
         nearbyPlaceItems.add(new NearbyPlaceItem("The Bullring", "1.5 Miles Away!", 3, R.drawable.bullring));


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


           /* SwipeLayout swipeLayout =  (SwipeLayout) rowView.findViewById(R.id.swipe);

//set show mode.
            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

//set drag edge.
            swipeLayout.setDragEdge(SwipeLayout.DragEdge.Right);

            swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onClose(SwipeLayout layout) {
                    //when the SurfaceView totally cover the BottomView.
                }

                @Override
                public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                    //you are swiping.
                }

                @Override
                public void onStartOpen(SwipeLayout swipeLayout) {

                }

                @Override
                public void onOpen(SwipeLayout layout) {
                    //when the BottomView totally show.
                }

                @Override
                public void onStartClose(SwipeLayout swipeLayout) {

                }

                @Override
                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                    //when user's hand released.
                }
            });
*/
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




