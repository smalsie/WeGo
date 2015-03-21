package uk.ac.aston.smalljh.wego;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uk.ac.aston.smalljh.wego.arrayadaptors.NoteArrayAdaptor;
import uk.ac.aston.smalljh.wego.fragments.UserInfo;
import uk.ac.aston.smalljh.wego.utils.DatePickerClass;
import uk.ac.aston.smalljh.wego.utils.GPlaces;

/**
 * Created by joshuahugh on 27/02/15.
 */
public class AddPlaceActivity extends ActionBarActivity implements AddCompanionDialog.AddCompanionDialogListener, AddNoteDialog.AddNoteDialogListener {

    private static final int LOCATION_RESULT_CODE = 1;

    private EditText location;

    private DatabaseHelper dh;

    private GPlaces place;

    private TextView dateButton;

    private String date;

    private static final int REQUEST_IMAGE_CAPTURE = 2;

    private static final int SELECT_PICTURE = 3;

    private EditText nameEditText;

    private ImageView image;

    private String mCurrentPhotoPath;

    private Button addCompanionBtn, addNoteBtn, submit;

    private List<Note> notes = new ArrayList<Note>();
    private List<String> companions = new ArrayList<String>();

    private List<String> notesTitle = new ArrayList<String>();

    private ArrayAdapter simpleAdapter;
    private NoteArrayAdaptor noteArrayAdaptor;

    private ListView companionsList,notesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_place);

        dh = new DatabaseHelper(getApplicationContext());

        location = (EditText)
                findViewById(R.id.add_place_location);

        image = (ImageView) findViewById(R.id.add_place_image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);

                //dispatchTakePictureIntent();
            }
        });

        ImageButton locationButton = (ImageButton) findViewById(R.id.add_place_locate_button);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapPane.class);
                startActivityForResult(intent, LOCATION_RESULT_CODE);

            }
        });

        dateButton = (TextView) findViewById(R.id.add_trip_date);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        String date = niceDate(year, month, day);

        dateButton.setText(date);

        submit = (Button) findViewById(R.id.add_place_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();

                Toast.makeText(getApplicationContext(), getText(R.string.place_added), Toast.LENGTH_LONG).show();

                finish();
            }
        });

        addCompanionBtn = (Button) findViewById(R.id.add_place_add_companion);
        addCompanionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCompanionDialog();
            }
        });

        addNoteBtn = (Button) findViewById(R.id.add_place_add_notes);
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNoteDialog();
            }
        });

        companionsList = (ListView) findViewById(R.id.add_place_companions_listview);
        notesList = (ListView) findViewById(R.id.add_place_notes_listview);

        nameEditText = (EditText) findViewById(R.id.add_place_name);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setElevation(0);


    }

    @Override
    public void onBackPressed() {


        new AlertDialog.Builder(this)
                .setTitle(R.string.quit_add_place_alert_title)
                .setMessage(R.string.quit_add_place_alert_message)
                .setPositiveButton(R.string.quit_add_place_alert_title, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (LOCATION_RESULT_CODE): {
                if (resultCode == Activity.RESULT_OK) {

                    place = (GPlaces) data.getParcelableExtra("Place");

                    location.setText(place.getVicinity());

                }
                break;
            }
            case (REQUEST_IMAGE_CAPTURE): {
                if (resultCode == Activity.RESULT_OK) {

                    handleCameraPhoto();
                }

                break;
            }
            case (SELECT_PICTURE): {
                if (resultCode == Activity.RESULT_OK) {

                    Uri selectedImageUri = data.getData();
                    mCurrentPhotoPath = getRealPathFromURI(selectedImageUri);
                    Log.i("PIC", selectedImageUri.toString());
                    Log.i("PIC", mCurrentPhotoPath);

                    setPic();
                }

                break;
            }

        }

    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String getRealPathFromURI(Uri contentUri) {
        String wholeID = DocumentsContract.getDocumentId(contentUri);

// Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

// where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = getContentResolver().
                query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{ id }, null);

        String filePath = "";

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }



        return filePath;
    }


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerClass();

        String date = dateButton.getText().toString();

        if(!date.equals("")) {

            int[] dateArr = getDayMonthYear(date);

            Bundle mBundle = new Bundle();
            mBundle.putInt("day", dateArr[0]);
            mBundle.putInt("month", dateArr[1]);
            mBundle.putInt("year", dateArr[2]);

            newFragment.setArguments(mBundle);
        }


        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void onFinishEditDialog(int year, int month, int day) {



        date = niceDate(year, month, day);

       dateButton.setText(date);
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


    private int[] getDayMonthYear(String date) {

        String[] dateStr = date.split("/");

        int[] dateArr = new int[3];

        for(int i = 0; i < 3; i++)
            dateArr[i] = Integer.parseInt(dateStr[i]);


        return dateArr;


    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }




    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = image.getWidth();
        int targetH = image.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        image.setImageBitmap(bitmap);
    }

    private void handleCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            setPic();
            galleryAddPic();

        }

    }


    private void submit() {
        String name = nameEditText.getText().toString();

        ContentValues c = new ContentValues();

        DatabaseHelper dh = new DatabaseHelper(getApplicationContext());

        if(place == null) {
            place = new GPlaces(location.getText().toString(), "", null, null);
        }

        c.put(dh.LocationLatitude, place.getLatitude());
        c.put(dh.LocationLongitude, place.getLongitude());
        c.put(dh.LocationName, place.getName());
        c.put(dh.LocationVicinity, place.getVicinity());

        long locationInsertID = dh.insert(dh.locationTable, c);


        UserInfo ui = new UserInfo(getApplicationContext());

        c = new ContentValues();

        c.put(dh.placesUserId, ui.getUserID());
        c.put(dh.placesTitle, name);
        c.put(dh.placesLocationID, locationInsertID);
        c.put(dh.placesDate, dateButton.getText().toString());

        long id = dh.insert(dh.placesTable,c);

        Log.i("SQL", id + "");

        for(String companionName : companions) {
            c = new ContentValues();

            c.put(dh.placesCompanionsPlaceId, id);
            c.put(dh.placesCompanionsCompanionName, companionName);

            long compID = dh.insert(dh.placesCompanionsTable, c);

            Log.i("SQL", compID + " Companion");

        }

        for(Note n : notes) {
            c = new ContentValues();

            c.put(dh.notesTitle, n.getTitle());
            c.put(dh.notesNote, n.getNote());

            long noteID = dh.insert(dh.notesTable, c);

            Log.i("SQL", noteID + " Note");

            c = new ContentValues();

            c.put(dh.placesNotesNotesId, noteID);
            c.put(dh.placesNotesPlaceId, id);

            long tripNotesID = dh.insert(dh.placesNotesTable, c);

            Log.i("SQL", tripNotesID + " trips Note");
        }




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
        noteArrayAdaptor = new NoteArrayAdaptor(this, notes);

        notesList.setAdapter(noteArrayAdaptor);

    }

    private void showAddCompanionDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AddCompanionDialog addCompanionDialog = new AddCompanionDialog();
        addCompanionDialog.show(fm, "fragment_edit_name");
    }
}