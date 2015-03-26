package uk.ac.aston.smalljh.wego;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import uk.ac.aston.smalljh.wego.utils.DatabaseHelper;
import uk.ac.aston.smalljh.wego.utils.GPlaces;

/**
 * Created by joshuahugh on 19/03/15.
 */
public class AddPlaceToTripDialog extends DialogFragment {

    private EditText search;

    public AddPlaceToTripDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_place_to_trip, container);
        search = (EditText) view.findViewById(R.id.add_place_to_trip_search);

        showPlaces("", view);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                showPlaces(search.getText().toString(), view);
            }
        });

        getDialog().setTitle(getString(R.string.add_place));

        return view;
    }


    public interface AddPlaceDialogListener {
        void onFinishPlaceDialog(long placeID);
    }



    private void cancel() {
        this.dismiss();
    }

    private class PlacesArrayAdaptor extends ArrayAdapter<PlaceItem> {
        private final Context context;
        private final ArrayList<PlaceItem> placeItems;

        public PlacesArrayAdaptor(Context context, ArrayList<PlaceItem> placeItems) {

            super(context, R.layout.home_fragment, placeItems);
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
            TextView title = (TextView) rowView.findViewById(R.id.place_title);
            TextView location = (TextView) rowView.findViewById(R.id.place_location);
            TextView date = (TextView) rowView.findViewById(R.id.place_date);
            ImageView image = (ImageView) rowView.findViewById(R.id.place_icon);

            image.setVisibility(View.GONE);

            title.setText(placeItem.getTitle());

            //int pic = placeItems.get(position).getPic();
            final GPlaces gPlaces = placeItem.getGPlace();

            if (gPlaces != null)
                location.setText(gPlaces.getName());

            date.setText(placeItem.getDate());
            //image.setImageResource(pic);

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submit(placeItem.getID());
                }
            });

            return rowView;
        }
    }

    private void submit(long placeID) {
        AddPlaceDialogListener activity = (AddPlaceDialogListener) getActivity();
        activity.onFinishPlaceDialog(placeID);
        this.dismiss();
    }

    private void showPlaces(String search, View view) {

        DatabaseHelper dh = new DatabaseHelper(getActivity().getApplicationContext());

        ArrayList<PlaceItem> placeItems = dh.getPlacesSearch(dh.getReadableDatabase(), search);


        final ListView listView = (ListView) view.findViewById(R.id.add_place_to_trip_listview);

        final PlacesArrayAdaptor arrayAdapter = new PlacesArrayAdaptor(getActivity(), placeItems);

        listView.setAdapter(arrayAdapter);
    }



}