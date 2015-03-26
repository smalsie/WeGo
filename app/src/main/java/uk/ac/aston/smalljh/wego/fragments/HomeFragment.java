package uk.ac.aston.smalljh.wego.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import uk.ac.aston.smalljh.wego.AddPlaceActivity;
import uk.ac.aston.smalljh.wego.HomeItem;
import uk.ac.aston.smalljh.wego.R;
import uk.ac.aston.smalljh.wego.TripItem;
import uk.ac.aston.smalljh.wego.utils.DatabaseHelper;

public class HomeFragment extends Fragment {
	
	 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {
         final View rootView = inflater.inflate(R.layout.home_main, container, false);

         DatabaseHelper dh = new DatabaseHelper(getActivity().getApplicationContext());

         ArrayList<TripItem> homeItems = dh.getTripsAsc(dh.getReadableDatabase());


         Button b = (Button) rootView.findViewById(R.id.home_add_place);

         b.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(rootView.getContext(), AddPlaceActivity.class);
                 startActivity(intent);
             }
         });



         final ListView listView = (ListView) rootView.findViewById(R.id.listview);


         final ContactArrayAdaptor arrayAdapter = new ContactArrayAdaptor(getActivity(), homeItems);

         listView.setAdapter(arrayAdapter);


         getActivity().setTitle("Home");
         return rootView;
     }




    private class ContactArrayAdaptor extends ArrayAdapter<TripItem> {
        private final Context context;
        private final ArrayList<TripItem> homeItems;

        public ContactArrayAdaptor(Context context,ArrayList<TripItem> homeItems) {

            super(context, R.layout.home_fragment,homeItems);
            this.context = context;
            this.homeItems = homeItems;
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
            View rowView = inflater.inflate(R.layout.home_item, parent, false);

            //Each of the textviews to add specified content to
            TextView title= (TextView) rowView.findViewById(R.id.nearby_place_location);
            TextView time= (TextView) rowView.findViewById(R.id.place_date);
            ImageView image = (ImageView) rowView.findViewById(R.id.nearby_place_icon);

            String titleText = "Joshua Small added the trip " + homeItems.get(position).getTitle();

            if(homeItems.get(position).getCompanions().size() > 0)
                titleText += "With,";

            for(String s : homeItems.get(position).getCompanions())
                titleText += " " + s + ",";

            //titleText = titleText.substring(0, titleText.length()-1);

            title.setText(titleText);

            int pic = R.drawable.me;


            time.setVisibility(View.GONE);
            image.setImageResource(pic);


            return rowView;
        }
    }
}




