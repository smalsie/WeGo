package uk.ac.aston.smalljh.wego;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import uk.ac.aston.smalljh.wego.utils.DatabaseHelper;
import uk.ac.aston.smalljh.wego.utils.GPlaces;

/**
 * Created by joshuahugh on 23/03/15.
 */
public abstract class AddObject extends ActionBarActivity {

    //result codes
    protected static final int LOCATION_RESULT_CODE = 1;
    protected static final int REQUEST_IMAGE_CAPTURE = 2;
    protected static final int SELECT_PICTURE = 3;

    protected static final int TAG_PICTURE = 4;

    //ui components
    protected EditText locationEditText, nameEditText;
    protected Button submit;
    protected ImageView image;

    protected DatabaseHelper dh;
    protected GPlaces place;

    protected String mCurrentPhotoPath = "";

    protected boolean editing = false;

    private boolean camera = false;

    protected long locID = 0;

    protected boolean isPlace = true;

    protected String tag = "";


    protected long imageLat = 0;
    protected long imageLng = 0;

    protected void getImage() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.picture_type)
                .setMessage(R.string.picture_type_title)
                .setPositiveButton(R.string.picture_type_camera, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dispatchTakePictureIntent();
                    }
                })
                .setNegativeButton(R.string.picture_type_storage, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getImageFromStorage();
                    }
                }).show();




    }

    protected void getImageFromStorage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    protected abstract long submit();



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

    protected void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    protected void setPic() {
        // Get the dimensions of the View

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        image.setImageBitmap(bitmap);
    }

    protected void handleCameraPhoto() {

        if (mCurrentPhotoPath != null) {

            Intent intent = new Intent(getApplicationContext(), ImageTagActivity.class);
            intent.putExtra("Picture", mCurrentPhotoPath);
            startActivityForResult(intent, TAG_PICTURE);

            camera = true;


        }

    }


    protected String niceDate(int year, int month, int day) {

        month++;

        String dayString = "" + day;
        String monthString = "" + month;

        if (day < 10)
            dayString = "0" + day;

        if (month < 10)
            monthString = "0" + month;

        return dayString + "/" + monthString + "/" + year;
    }


    protected int[] getDayMonthYear(String date) {

        String[] dateStr = date.split("/");

        int[] dateArr = new int[3];

        for (int i = 0; i < 3; i++)
            dateArr[i] = Integer.parseInt(dateStr[i]);


        return dateArr;


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

    @Override
    public void onBackPressed() {

        String title = "";
        String message = "";

        if(isPlace) {

            if (editing) {
                title = getResources().getString(R.string.quit_edit_place_alert_title);
                message = getResources().getString(R.string.quit_edit_place_alert_message);
            } else {
                title = getResources().getString(R.string.quit_add_place_alert_title);
                message = getResources().getString(R.string.quit_add_place_alert_message);
            }

        } else {

            if (editing) {
                title = getResources().getString(R.string.quit_edit_trip_alert_title);
                message = getResources().getString(R.string.quit_edit_trip_alert_message);
            } else {
                title = getResources().getString(R.string.quit_add_trip_alert_title);
                message = getResources().getString(R.string.quit_add_trip_alert_message);
            }

        }

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(title, new DialogInterface.OnClickListener() {
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

                    locationEditText.setText(place.getName() + ", " + place.getVicinity());

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

                    imageLat = data.getLongExtra("LAT", 0);
                    imageLng = data.getLongExtra("LNG", 0);

                }

                break;
            }

        }

    }





}
