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
import java.util.List;

import uk.ac.aston.smalljh.wego.PlaceItem;
import uk.ac.aston.smalljh.wego.R;

public class PlacesCompanionsFragment extends PlacesFrag {
	
	 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.places_companions, container, false);

         List<String> companions = placeItem.getCompanions();

         TextView none = (TextView) rootView.findViewById(R.id.places_companions_none);

         if(companions.size() != 0) {

             ListView companionsList = (ListView) rootView.findViewById(R.id.places_companions_listview);
             companionsList.setVisibility(View.VISIBLE);

             none.setVisibility(View.GONE);

             ArrayAdapter adaptor = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, companions);

             companionsList.setAdapter(adaptor);

         }

         return rootView;
     }

    @Override
    public String getName() {
        return "Companions";
    }



}




