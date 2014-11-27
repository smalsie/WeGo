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

import uk.ac.aston.smalljh.wego.HomeItem;
import uk.ac.aston.smalljh.wego.R;

public class HomeFragment extends Fragment {
	
	 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.home_main, container, false);


         ArrayList<HomeItem> homeItems = new ArrayList<HomeItem>();

         homeItems.add(new HomeItem("Joshua Small added the trip New York with Courtney Sullivan!", "5 mins ago"));
         homeItems.add(new HomeItem("Joshua Small added the trip New York with Courtney Sullivan!", "5 mins ago"));



         final ListView listView = (ListView) rootView.findViewById(R.id.listview);

         final ContactArrayAdaptor arrayAdapter = new ContactArrayAdaptor(getActivity(), homeItems);

         listView.setAdapter(arrayAdapter);


         getActivity().setTitle("Home");
         return rootView;
     }




    private class ContactArrayAdaptor extends ArrayAdapter<HomeItem> {
        private final Context context;
        private final ArrayList<HomeItem> homeItems;

        public ContactArrayAdaptor(Context context,ArrayList<HomeItem> homeItems) {

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
            TextView title= (TextView) rowView.findViewById(R.id.place_location);
            TextView time= (TextView) rowView.findViewById(R.id.place_date);
            ImageView image = (ImageView) rowView.findViewById(R.id.place_icon);

            title.setText(homeItems.get(position).getTitle());

            int pic = R.drawable.me;


            time.setText(homeItems.get(position).getTime());
            image.setImageResource(pic);


            return rowView;
        }
    }
}




