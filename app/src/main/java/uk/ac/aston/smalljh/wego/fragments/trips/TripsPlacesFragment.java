package uk.ac.aston.smalljh.wego.fragments.trips;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aston.smalljh.wego.AddNoteDialog;
import uk.ac.aston.smalljh.wego.AddPlaceToTripDialog;
import uk.ac.aston.smalljh.wego.Note;
import uk.ac.aston.smalljh.wego.PlaceItem;
import uk.ac.aston.smalljh.wego.PlaceViewActivity;
import uk.ac.aston.smalljh.wego.R;
import uk.ac.aston.smalljh.wego.arrayadaptors.NoteArrayAdaptor;
import uk.ac.aston.smalljh.wego.utils.GPlaces;

public class TripsPlacesFragment extends TripsFrag {

    private View rootView;

	 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {

         setRetainInstance(true);
         super.onCreate(savedInstanceState);

         rootView = inflater.inflate(R.layout.trip_places, container, false);

         loadList();

         Button addNotesButton = (Button) rootView.findViewById(R.id.trip_add_place);
         addNotesButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showAddPlaceDialog();
             }
         });

         return rootView;
     }

    @Override
    public String getName() {
        return "Places";
    }





    private void loadList() {
        List<PlaceItem> placeItems = tripItem.getPlaces();

        TextView none = (TextView) rootView.findViewById(R.id.places_notes_none);
        ListView companionsList = (ListView) rootView.findViewById(R.id.listview);

        none.setVisibility(View.GONE);
        companionsList.setVisibility(View.GONE);

        if(placeItems.size() != 0) {


            companionsList.setVisibility(View.VISIBLE);

            none.setVisibility(View.GONE);

            PlacesArrayAdaptor adaptor = new PlacesArrayAdaptor(getActivity().getApplicationContext(), placeItems);

            companionsList.setAdapter(adaptor);

        } else {
            none.setVisibility(View.VISIBLE);

            none.setText("No Places");
            companionsList.setVisibility(View.GONE);
        }
    }

    @Override
    public void refresh() {
        loadList();
    }

    private void showAddPlaceDialog() {
        FragmentManager fm = getChildFragmentManager();
        AddPlaceToTripDialog addCompanionDialog = new AddPlaceToTripDialog();
        addCompanionDialog.show(fm, "fragment_edit_name");
    }

    private class PlacesArrayAdaptor extends ArrayAdapter<PlaceItem> {
        private final Context context;
        private final List<PlaceItem> placeItems;

        public PlacesArrayAdaptor(Context context,List<PlaceItem> placeItems) {

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
            title.setVisibility(View.VISIBLE);
            TextView location= (TextView) rowView.findViewById(R.id.place_location);
            location.setVisibility(View.VISIBLE);
            TextView date= (TextView) rowView.findViewById(R.id.place_date);
            date.setVisibility(View.VISIBLE);
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

            if(!placeItem.getPic().equals("")) {

                image.setImageBitmap(placeItem.getPic().getImage());

                image.invalidate();
            }


            return rowView;
        }

        private void setPic(ImageView image, String picture) {
            // Get the dimensions of the View

        }

    }

}




