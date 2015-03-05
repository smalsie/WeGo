package uk.ac.aston.smalljh.wego.fragments.places;


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

import uk.ac.aston.smalljh.wego.PlaceItem;
import uk.ac.aston.smalljh.wego.R;

public class PlacesMapFragment extends PlacesFrag {
	
	 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.places_main, container, false);


         ArrayList<PlaceItem> placeItems = new ArrayList<PlaceItem>();

         placeItems.add(new PlaceItem("Eiffel Tower", "Paris, France", "You were here October 27th 2014", R.drawable.eifel));
         placeItems.add(new PlaceItem("Arc de Triomphe", "Paris, France", "You were here from October 28th 2014", R.drawable.arc1));


         final ListView listView = (ListView) rootView.findViewById(R.id.listview);

         final ContactArrayAdaptor arrayAdapter = new ContactArrayAdaptor(getActivity(), placeItems);

         listView.setAdapter(arrayAdapter);


         getActivity().setTitle(R.string.your_places);
         return rootView;
     }

    @Override
    public String getName() {
        return "Map";
    }


    private class ContactArrayAdaptor extends ArrayAdapter<PlaceItem> {
        private final Context context;
        private final ArrayList<PlaceItem> placeItems;

        public ContactArrayAdaptor(Context context,ArrayList<PlaceItem> placeItems) {

            super(context, R.layout.home_fragment,placeItems);
            this.context = context;
            this.placeItems = placeItems;
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
            View rowView = inflater.inflate(R.layout.place_item, parent, false);

            //Each of the textviews to add specified content to
            TextView title= (TextView) rowView.findViewById(R.id.nearby_place_location);
            TextView city= (TextView) rowView.findViewById(R.id.nearby_place_distance_away);
            TextView time= (TextView) rowView.findViewById(R.id.place_date);
            ImageView image = (ImageView) rowView.findViewById(R.id.nearby_place_icon);

            title.setText(placeItems.get(position).getTitle());

            int pic = placeItems.get(position).getPic();

            city.setText(placeItems.get(position).getCity());

            time.setText(placeItems.get(position).getTime());
            image.setImageResource(pic);


            return rowView;
        }

    }
}




