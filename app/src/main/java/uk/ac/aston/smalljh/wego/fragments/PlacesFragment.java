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

import uk.ac.aston.smalljh.wego.utils.DatabaseHelper;
import uk.ac.aston.smalljh.wego.PlaceItem;
import uk.ac.aston.smalljh.wego.PlaceViewActivity;
import uk.ac.aston.smalljh.wego.R;
import uk.ac.aston.smalljh.wego.utils.GPlaces;

public class PlacesFragment extends Fragment {


	 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.trips_main, container, false);

         DatabaseHelper dh = new DatabaseHelper(getActivity().getApplicationContext());
         SQLiteDatabase db = dh.getReadableDatabase();

         ArrayList<PlaceItem> placeItems = dh.getPlaces(db);


         final ListView listView = (ListView) rootView.findViewById(R.id.listview);

         final ContactArrayAdaptor arrayAdapter = new ContactArrayAdaptor(getActivity(), placeItems);

         listView.setAdapter(arrayAdapter);

         Button button = (Button) rootView.findViewById(R.id.)


         getActivity().setTitle(R.string.your_places);
         return rootView;
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

            final PlaceItem placeItem = placeItems.get(position);

            //Each of the textviews to add specified content to
            TextView title= (TextView) rowView.findViewById(R.id.place_title);
            TextView location= (TextView) rowView.findViewById(R.id.place_location);
            TextView date= (TextView) rowView.findViewById(R.id.place_date);
            ImageView image = (ImageView) rowView.findViewById(R.id.place_icon);

            title.setText(placeItem.getTitle());

            //int pic = placeItems.get(position).getPic();
            GPlaces gPlaces = placeItem.getGPlace();

            if(gPlaces != null)
                location.setText(gPlaces.getName());

            date.setText(placeItem.getDate());
            //image.setImageResource(pic);

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), PlaceViewActivity.class);

                    intent.putExtra("Place", placeItem.getID());

                    startActivity(intent);
                }
            });

            if((placeItem.getPic() != null) && (placeItem.getPic().getID() > 0)) {

                image.setImageBitmap(placeItem.getPic().getImage());

                image.invalidate();
            }


            return rowView;
        }

        private void setPic(ImageView image, String picture) {
            // Get the dimensions of the View

        }

        public String getName() {
            return "Places";
        }
    }
}




