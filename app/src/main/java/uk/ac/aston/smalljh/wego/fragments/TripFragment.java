package uk.ac.aston.smalljh.wego.fragments;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;

import uk.ac.aston.smalljh.wego.DatabaseHelper;
import uk.ac.aston.smalljh.wego.R;
import uk.ac.aston.smalljh.wego.TripItem;

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


              SwipeLayout swipeLayout =  (SwipeLayout) rowView.findViewById(R.id.trip_swipe);

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


            //Each of the textviews to add specified content to
            TextView title= (TextView) rowView.findViewById(R.id.trip_name);
            TextView city= (TextView) rowView.findViewById(R.id.trip_location);
            TextView time= (TextView) rowView.findViewById(R.id.trip_date);
            ImageView image = (ImageView) rowView.findViewById(R.id.trip_icon);

            title.setText(tripItems.get(position).getTitle());

            //int pic = tripItems.get(position).getPic();

            city.setText(tripItems.get(position).getLocation());

            time.setText(tripItems.get(position).getStartDate());
            //image.setImageResource(pic);


            return rowView;
        }
    }
}




