package uk.ac.aston.smalljh.wego;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aston.smalljh.wego.fragments.UserInfo;
import uk.ac.aston.smalljh.wego.utils.DatePickerClass;
import uk.ac.aston.smalljh.wego.utils.GPlaces;

/**
 * Created by joshuahugh on 27/02/15.
 */
public class AddTripActivity extends ActionBarActivity implements AddCompanionDialog.AddCompanionDialogListener, AddNoteDialog.AddNoteDialogListener {

    private String name = "";
    private String startDate = "";
    private String endDate = "";

    private int returnCode = 100;

    private GPlaces place;

    private EditText nameInput, locationInput;

    private Button startDateBtn, endDateBtn, addCompanionBtn, addNoteBtn, submitBtn;

    private List<String> companions = new ArrayList<String>();

    private List<String> notesTitle = new ArrayList<String>();

    private List<Note> notes = new ArrayList<Note>();

    private ListView companionsList,notesList;

    private ArrayAdapter simpleAdapter, simpleAdapter2;

    private final int START_DATE_REQUEST_CODE = 1;
    private final int END_DATE_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_trip);

        nameInput = (EditText) findViewById(R.id.add_trip_name);
        locationInput = (EditText) findViewById(R.id.add_trip_location);

        startDateBtn = (Button) findViewById(R.id.add_trip_start_date);
        startDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v, START_DATE_REQUEST_CODE);
            }
        });

        endDateBtn = (Button) findViewById(R.id.add_trip_end_date);
        endDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v, END_DATE_REQUEST_CODE);
            }
        });

        addCompanionBtn = (Button) findViewById(R.id.add_trip_add_companion);
        addCompanionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCompanionDialog();
            }
        });

        addNoteBtn = (Button) findViewById(R.id.add_trip_add_notes);
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNoteDialog();
            }
        });

        submitBtn = (Button) findViewById(R.id.add_trip_submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        companionsList = (ListView) findViewById(R.id.add_trip_companions_listview);
        notesList = (ListView) findViewById(R.id.add_trip_notes_listview);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setElevation(0);

    }

    public void showTimePickerDialog(View v, int returnCode) {
        DialogFragment newFragment = new DatePickerClass();

        this.returnCode = returnCode;

        Bundle mBundle = new Bundle();

        if(!startDate.equals("")) {

            int[] dateArr = getDayMonthYear(startDate);


            mBundle.putInt("day", dateArr[0]);
            mBundle.putInt("month", dateArr[1]);
            mBundle.putInt("year", dateArr[2]);

            newFragment.setArguments(mBundle);
        }

        mBundle.putInt("returnCode", returnCode);


        newFragment.show(getFragmentManager(), "timePicker");
    }

    private int[] getDayMonthYear(String date) {

        String[] dateStr = date.split("/");

        int[] dateArr = new int[3];

        for(int i = 0; i < 3; i++)
            dateArr[i] = Integer.parseInt(dateStr[i]);


        return dateArr;


    }

    public void onFinishEditDialog(int year, int month, int day) {

        if(returnCode == START_DATE_REQUEST_CODE) {

            startDate = niceDate(year, month, day);

            startDateBtn.setText(startDate);

        } else if(returnCode == END_DATE_REQUEST_CODE) {

            endDate = niceDate(year, month, day);

            endDateBtn.setText(endDate);

        }


    }

    private String niceDate(int year, int month, int day) {

        month++;

        String dayString = "" + day;
        String monthString = "" + month;

        if(day < 10)
            dayString = "0" + day;

        if(month < 10)
            monthString = "0" + month;

        return dayString + "/" + monthString + "/" + year;
    }

    private void showAddCompanionDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AddCompanionDialog addCompanionDialog = new AddCompanionDialog();
        addCompanionDialog.show(fm, "fragment_edit_name");
    }

    private void showAddNoteDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AddNoteDialog addCompanionDialog = new AddNoteDialog();
        addCompanionDialog.show(fm, "fragment_edit_name");
    }

    @Override
    public void onFinishEditDialog(String inputText) {
       companions.add(inputText);

        companionsListRefresh();

    }


    private void companionsListRefresh() {
        simpleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, companions);

        companionsList.setAdapter(simpleAdapter);

    }

    @Override
    public void onFinishNoteEditDialog(String title, String note) {
        notes.add(new Note(title, note));
        notesTitle.add(title);

        notesListRefresh();
    }

    private void notesListRefresh() {
        simpleAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notesTitle);

        notesList.setAdapter(simpleAdapter2);

    }

    private void submit() {
        name = nameInput.getText().toString();

        ContentValues c = new ContentValues();

        DatabaseHelper dh = new DatabaseHelper(getApplicationContext());

        UserInfo ui = new UserInfo(getApplicationContext());

        c.put(dh.tripsUserId, ui.getUserID());
        c.put(dh.tripsTitle, name);
        c.put(dh.tripsLocation, locationInput.getText().toString());
        c.put(dh.tripsStartDate, startDate);
        c.put(dh.tripsEndDate, endDate);

        long id = dh.insert(dh.tripsTable,c);

        Log.i("SQL", id + "");

        for(String companionName : companions) {
            c = new ContentValues();

            c.put(dh.tripsCompanionsId, id);
            c.put(dh.tripsCompanionsName, companionName);

            long compID = dh.insert(dh.tripsCompanionsTable, c);

            Log.i("SQL", compID + " Companion");

        }

        for(Note n : notes) {
            c = new ContentValues();

            c.put(dh.notesTitle, n.getTitle());
            c.put(dh.notesNote, n.getNote());

            long noteID = dh.insert(dh.notesTable, c);

            Log.i("SQL", noteID + " Note");

            c = new ContentValues();

            c.put(dh.tripsNotesNotesId, noteID);
            c.put(dh.tripsNotesTripId, id);

            long tripNotesID = dh.insert(dh.tripsNotesTable, c);

            Log.i("SQL", tripNotesID + " trips Note");
        }




    }
}
