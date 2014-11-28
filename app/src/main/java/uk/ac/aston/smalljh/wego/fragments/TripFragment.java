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
import uk.ac.aston.smalljh.wego.R;
import uk.ac.aston.smalljh.wego.TripItem;

public class TripFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.places_main, container, false);


        ArrayList<TripItem> tripItems = new ArrayList<TripItem>();

        String[] companions = {"Courtney Sullivan"};

        tripItems.add(new TripItem("Paris", companions, "You were here from October 27th 2014 to October 30th 2014", R.drawable.eifel));
        tripItems.add(new TripItem("Dublin", companions, "You were here from November 28th 2014 to November 29th 2014", R.drawable.dublin));


        final ListView listView = (ListView) rootView.findViewById(R.id.listview);

        final ContactArrayAdaptor arrayAdapter = new ContactArrayAdaptor(getActivity(), tripItems);

        listView.setAdapter(arrayAdapter);


        getActivity().setTitle(R.string.your_trips);
        return rootView;
    }




    private class ContactArrayAdaptor extends ArrayAdapter<TripItem> {
        private final Context context;
        private final ArrayList<TripItem> tripItems;

        public ContactArrayAdaptor(Context context,ArrayList<TripItem> homeItems) {

            super(context, R.layout.home_fragment,homeItems);
            this.context = context;
            this.tripItems = homeItems;
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

            title.setText(tripItems.get(position).getTitle());

            int pic = tripItems.get(position).getPic();

            city.setText(tripItems.get(position).getCompanions());

            time.setText(tripItems.get(position).getTime());
            image.setImageResource(pic);


            return rowView;
        }
    }
}




