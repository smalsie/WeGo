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

public class PlacesOverviewFragment extends PlacesFrag {

	 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.place_overview, container, false);


         TextView title = (TextView) rootView.findViewById(R.id.place_overview_title);

         title.setText(placeItem.getTitle());


         return rootView;
     }

    @Override
    public String getName() {
        return "Overview";
    }



}




