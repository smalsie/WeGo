package uk.ac.aston.smalljh.wego;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.astuetz.PagerSlidingTabStrip;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.ac.aston.smalljh.wego.fragments.trips.TripsCompanionsFragment;
import uk.ac.aston.smalljh.wego.fragments.trips.TripsFrag;
import uk.ac.aston.smalljh.wego.fragments.trips.TripsGalleryFragment;
import uk.ac.aston.smalljh.wego.fragments.trips.TripsNotesFragment;
import uk.ac.aston.smalljh.wego.fragments.trips.TripsOverviewFragment;
import uk.ac.aston.smalljh.wego.fragments.trips.TripsPlacesFragment;
import uk.ac.aston.smalljh.wego.utils.DatabaseHelper;
import uk.ac.aston.smalljh.wego.utils.ImageItem;


public class TripViewActivity extends ActionBarActivity implements AddCompanionDialog.AddCompanionDialogListener, AddNoteDialog.AddNoteDialogListener, AddPlaceToTripDialog.AddPlaceDialogListener {

    private PagerSlidingTabStrip tabs;
    private TripItem tripItem;
    private TripsViewAdapter tripsViewAdapter;

    private ViewPager pager;

    private Handler messageHandler = new Handler();

    private long tripID;

    private final int EDIT_TRIP_CODE = 1;

    public static final int REQUEST_IMAGE_CAPTURE = 2;
    public static final int SELECT_PICTURE = 3;

    public static final int TAG_PICTURE = 4;

    protected String mCurrentPhotoPath = "";


    private boolean camera = false;


    protected String tag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_view);

        tripID = getIntent().getLongExtra("TRIP", 0);

        int value = getIntent().getIntExtra("Value", 0);

        if(value == SELECT_PICTURE)
            getImageFromStorage();
        else if(value == REQUEST_IMAGE_CAPTURE)
            dispatchTakePictureIntent();



        if((int)tripID == 0)
            finish();

        DatabaseHelper dh = new DatabaseHelper(getApplicationContext());

        SQLiteDatabase db = dh.getReadableDatabase();

        tripItem = dh.getTrip(db, tripID);

        Log.i("LATLNG", tripItem.getGPlace().getLatitude() + " : " + tripItem.getGPlace().getLongitude());

        tripsViewAdapter = new TripsViewAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.places_pager);
        pager.setAdapter(tripsViewAdapter);

        // Bind the tabs to the ViewPager
        tabs = (PagerSlidingTabStrip) findViewById(R.id.places_tabs);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setTextColorResource(R.color.white);


        tabs.setIndicatorColorResource(R.color.white);
        tabs.setShouldExpand(true);

        changeColor(getResources().getColor(R.color.white));

        tabs.setViewPager(pager);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.dark_purple));

        }


        getSupportActionBar().setTitle(tripItem.getTitle());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setElevation(0);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_place_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {

            Intent intent = new Intent(getApplicationContext(), AddTripActivity.class);

            intent.putExtra("TripID", tripItem.getID());

            startActivityForResult(intent, EDIT_TRIP_CODE);

            return true;
        } else if (id == R.id.action_view_map) {
            Intent intent = new Intent(getApplicationContext(), MapActivity.class);

            intent.putExtra("TRIP", tripID);

            startActivity(intent);

            return true;
        } else if(id == R.id.menu_item_share) {

            String text = "Have a look at Joshes Place, " + tripItem.getTitle();

            Intent sharingIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            sharingIntent.setType("*/*");

            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Joshes Trip, " + tripItem.getTitle());

            ArrayList<Uri> imageUris = new ArrayList<Uri>();


            if((tripItem.getPic() != null) && (tripItem.getPic().getID() > 0)) {
                String pathofBmp = MediaStore.Images.Media.insertImage(getContentResolver(), tripItem.getPic().getImage(), tripItem.getPic().getTitle(), null);
                Uri bmpUri = Uri.parse(pathofBmp);

                imageUris.add(bmpUri);
            }



            List<Note> notes = tripItem.getNotes();



            if(notes.size() > 0) {

                text += "\nNotes:\n";

                for (Note n : notes) {
                    text += n.getTitle() + ", \n" + n.getNote() + "\n";


                }

            }

            List<String> companions = tripItem.getCompanions();

            if(companions.size() > 0) {

                text += "\nCompanions:\n";

                for (String s : companions) {
                    text += s + "\n";


                }

            }

            List<PlaceItem> places = tripItem.getPlaces();

            if(places.size() > 0) {

                text += "\nPlaces Visited: \n";

                for(PlaceItem p : places) {
                    text += p.getTitle() + " (" + p.getDate() + ")\n";
                }
            }

            text +="\nHere on: " + tripItem.getStartDate() + " to " + tripItem.getEndDate();



            ArrayList<ImageItem> images = tripItem.getImages();


            if(images.size() > 0) {

                sharingIntent.putExtra(Intent.EXTRA_TEXT, "\nImages:\n");



                for (ImageItem i : images) {
                    String pathofBmp = MediaStore.Images.Media.insertImage(getContentResolver(), i.getImage(), i.getTitle(), null);
                    Uri bmpUri = Uri.parse(pathofBmp);

                    imageUris.add(bmpUri);



                }

                sharingIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);

            }

            sharingIntent.putExtra(Intent.EXTRA_TEXT, text);






            startActivity(Intent.createChooser(sharingIntent, "Share trip using"));
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        tripItem.putCompanion(inputText, getApplicationContext());
        tripsViewAdapter.refresh(1);

        pager.setCurrentItem(1, false);

    }

    @Override
    public void onFinishNoteEditDialog(String title, String note) {
        tripItem.putNote(title, note, getApplicationContext());
        tripsViewAdapter.refresh(2);

        pager.setCurrentItem(2, false);
    }

    @Override
    public void onFinishPlaceDialog(long placeID) {
        tripItem.putPlace(placeID, getApplicationContext());

        tripsViewAdapter.refresh(3);

        pager.setCurrentItem(3, false);

    }

    private class TripsViewAdapter extends FragmentPagerAdapter {

        private FragmentManager fm;
        private List<TripsFrag> fragments;

        public TripsViewAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;

            this.fragments = new ArrayList<TripsFrag>();
            fragments.add(new TripsOverviewFragment());

            fragments.add(new TripsCompanionsFragment());
            fragments.add(new TripsNotesFragment());
            fragments.add(new TripsPlacesFragment());
            fragments.add(new TripsGalleryFragment());

            for(TripsFrag tf : fragments)
                tf.setTripItem(tripItem);

        }

        @Override
        public TripsFrag getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments.get(position).getName();
        }

        public void refresh(int index) {
            fragments.get(index).refresh();
        }
    }

    private void changeColor(int newColor) {

        tabs.setIndicatorColor(newColor);

        // change ActionBar color just if an ActionBar is available
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            Drawable colorDrawable = new ColorDrawable(newColor);
            Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_background);
            LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });

        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (EDIT_TRIP_CODE): {
                if (resultCode == Activity.RESULT_OK) {

                    final Activity activity = this;
                    messageHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.w("Handler...", "Recreate requested.");
                            activity.recreate();

                            tripsViewAdapter.getItem(0).refresh();
                        }
                    }, 1);

                }
                break;
            }case (REQUEST_IMAGE_CAPTURE): {
                if (resultCode == Activity.RESULT_OK) {

                    handleCameraPhoto();
                }

                break;
            }
            case (SELECT_PICTURE): {
                if (resultCode == Activity.RESULT_OK) {

                    Uri selectedImageUri = data.getData();
                    mCurrentPhotoPath = getRealPathFromURI(selectedImageUri);

                    camera = false;

                    handleCameraPhoto();
                }

                break;
            }
            case (TAG_PICTURE): {
                if (resultCode == Activity.RESULT_OK) {


                    setPic();

                    if(camera)
                        galleryAddPic();

                    tag = data.getStringExtra("Tag");

                    //placeID = data.getIntExtra("PlaceID", 0);


                    long lat = data.getLongExtra("LAT", 0);
                    long lng = data.getLongExtra("LNG", 0);

                    tripItem.putImage(mCurrentPhotoPath, tag, getApplicationContext(), lat, lng);

                    tripsViewAdapter.refresh(4);

                    pager.setCurrentItem(4, false);

                }

                break;
            }
        }

    }





    protected void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    protected void setPic() {
        // Get the dimensions of the View

        //Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        //image.setImageBitmap(bitmap);
    }

    protected void handleCameraPhoto() {

        if (mCurrentPhotoPath != null) {

            Intent intent = new Intent(getApplicationContext(), ImageTagActivity.class);
            intent.putExtra("Picture", mCurrentPhotoPath);
            startActivityForResult(intent, TAG_PICTURE);

            camera = true;


        }

    }

    protected void getImageFromStorage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }



    protected void dispatchTakePictureIntent() {
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


    protected File createImageFile() throws IOException {
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






    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected String getRealPathFromURI(Uri contentUri) {
        String wholeID = DocumentsContract.getDocumentId(contentUri);

// Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Images.Media.DATA};

// where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = getContentResolver().
                query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{id}, null);

        String filePath = "";

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }


        return filePath;
    }
}
