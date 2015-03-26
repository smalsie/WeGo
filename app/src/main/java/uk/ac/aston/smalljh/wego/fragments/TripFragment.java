package uk.ac.aston.smalljh.wego.fragments;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import uk.ac.aston.smalljh.wego.AddTripActivity;
import uk.ac.aston.smalljh.wego.MapPane;
import uk.ac.aston.smalljh.wego.TripViewActivity;
import uk.ac.aston.smalljh.wego.utils.DatabaseHelper;
import uk.ac.aston.smalljh.wego.PlaceViewActivity;
import uk.ac.aston.smalljh.wego.R;
import uk.ac.aston.smalljh.wego.TripItem;
import uk.ac.aston.smalljh.wego.utils.GPlaces;

public class TripFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.trip_places, container, false);

        DatabaseHelper dh = new DatabaseHelper(getActivity().getApplicationContext());
        SQLiteDatabase db = dh.getReadableDatabase();

        ArrayList<TripItem> tripItems = dh.getTrips(db);

        final ListView listView = (ListView) rootView.findViewById(R.id.listview);

        final ContactArrayAdaptor arrayAdapter = new ContactArrayAdaptor(getActivity(), tripItems);

        listView.setAdapter(arrayAdapter);

        Button addTrip = (Button) rootView.findViewById(R.id.trip_add_place);
        addTrip.setText("AddTrip");

        addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AddTripActivity.class);
                startActivity(intent);
            }
        });


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

            View rowView = inflater.inflate(R.layout.trip_item, parent, false);


            final TripItem tripItem = tripItems.get(position);

            //Each of the textviews to add specified content to
            TextView title= (TextView) rowView.findViewById(R.id.trip_name);
            TextView location= (TextView) rowView.findViewById(R.id.trip_location);
            TextView date= (TextView) rowView.findViewById(R.id.trip_date);
            ImageView image = (ImageView) rowView.findViewById(R.id.trip_icon);

            title.setText(tripItem.getTitle());

            //int pic = placeItems.get(position).getPic();
            GPlaces gPlaces = tripItem.getGPlace();

            if(gPlaces != null)
                location.setText(gPlaces.getName());

            date.setText(tripItem.getStartDate() + " - " + tripItem.getEndDate());
            //image.setImageResource(pic);

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), TripViewActivity.class);

                    intent.putExtra("TRIP", tripItem.getID());

                    startActivity(intent);
                }
            });

            if((tripItem.getPic() != null) && (tripItem.getPic().getID() > 0)) {

                image.setImageBitmap(tripItem.getPic().getImage());

                image.invalidate();
            }


            return rowView;
        }
    }
}




