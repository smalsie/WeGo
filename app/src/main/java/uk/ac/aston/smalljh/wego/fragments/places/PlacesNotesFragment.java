package uk.ac.aston.smalljh.wego.fragments.places;


import android.content.Context;
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

import com.daimajia.swipe.SwipeLayout;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aston.smalljh.wego.AddNoteDialog;
import uk.ac.aston.smalljh.wego.HomeItem;
import uk.ac.aston.smalljh.wego.Note;
import uk.ac.aston.smalljh.wego.R;
import uk.ac.aston.smalljh.wego.arrayadaptors.NoteArrayAdaptor;

public class PlacesNotesFragment extends PlacesFrag {

    private View rootView;

	 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {

         setRetainInstance(true);
         super.onCreate(savedInstanceState);

         rootView = inflater.inflate(R.layout.places_notes, container, false);

         loadList();

         Button addNotesButton = (Button) rootView.findViewById(R.id.place_notes_add_button);
         addNotesButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showAddNoteDialog();
             }
         });

         return rootView;
     }

    @Override
    public String getName() {
        return "Notes";
    }





    private void loadList() {
        List<Note> notes = placeItem.getNotes();

        TextView none = (TextView) rootView.findViewById(R.id.places_notes_none);
        ListView companionsList = (ListView) rootView.findViewById(R.id.places_notes_listview);

        none.setVisibility(View.GONE);
        companionsList.setVisibility(View.GONE);

        if(notes.size() != 0) {


            companionsList.setVisibility(View.VISIBLE);

            none.setVisibility(View.GONE);

            NoteArrayAdaptor adaptor = new NoteArrayAdaptor(getActivity().getApplicationContext(), notes);

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

    private void showAddNoteDialog() {
        FragmentManager fm = getChildFragmentManager();
        AddNoteDialog addCompanionDialog = new AddNoteDialog();
        addCompanionDialog.show(fm, "fragment_edit_name");
    }

}




