package uk.ac.aston.smalljh.wego.fragments.places;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import uk.ac.aston.smalljh.wego.AddCompanionDialog;
import uk.ac.aston.smalljh.wego.DatabaseHelper;
import uk.ac.aston.smalljh.wego.PlaceItem;
import uk.ac.aston.smalljh.wego.R;

public class PlacesCompanionsFragment extends PlacesFrag {

    private View rootView;

	 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {

         setRetainInstance(true);
         super.onCreate(savedInstanceState);

         rootView = inflater.inflate(R.layout.places_companions, container, false);

         loadList();

         Button addCompanion = (Button) rootView.findViewById(R.id.place_companion_add_button);
         addCompanion.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showAddCompanionDialog();
             }
         });

         return rootView;
     }

    @Override
    public String getName() {
        return "Companions";
    }


    private void showAddCompanionDialog() {
        FragmentManager fm = getChildFragmentManager();
        AddCompanionDialog addCompanionDialog = new AddCompanionDialog();
        addCompanionDialog.show(fm, "fragment_edit_name");
    }

    private void loadList() {
        List<String> companions = placeItem.getCompanions();

        TextView none = (TextView) rootView.findViewById(R.id.places_companions_none);
        ListView companionsList = (ListView) rootView.findViewById(R.id.places_companions_listview);

        none.setVisibility(View.GONE);
        companionsList.setVisibility(View.GONE);

        if(companions.size() != 0) {


            companionsList.setVisibility(View.VISIBLE);

            none.setVisibility(View.GONE);

            ArrayAdapter adaptor = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, companions);

            companionsList.setAdapter(adaptor);

        } else {
            none.setVisibility(View.VISIBLE);
            companionsList.setVisibility(View.GONE);
        }
    }

    @Override
    public void refresh() {
        loadList();
    }
}




