package uk.ac.aston.smalljh.wego;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;


public class ImageTagActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_tag);

        final String picture = getIntent().getStringExtra("Picture");


        if(picture.equals(""))
            finish();

        Bitmap bitmap = BitmapFactory.decodeFile(picture);


        ImageView image = (ImageView) findViewById(R.id.image_tag_image);

        image.setImageBitmap(bitmap);

        final EditText tag = (EditText) findViewById(R.id.image_tag_tag);

        Button submit = (Button) findViewById(R.id.image_tag_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LatLng latlng = null;
                try {
                    latlng = getLatLng(picture);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra("Tag", tag.getText().toString());
                resultIntent.putExtra("LAT", latlng.latitude);
                resultIntent.putExtra("LNG", latlng.longitude);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        Button cancel = (Button) findViewById(R.id.image_tag_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setTitle("Tag Photo");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_tag, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static LatLng getLatLng(String picture) throws IOException {

        ExifInterface exif = new ExifInterface(picture);
        String LATITUDE = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
        String LATITUDE_REF = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
        String LONGITUDE = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
        String LONGITUDE_REF = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);


        Float Latitude = 0F;
        Float Longitude = 0F;

        if ((LATITUDE != null)
                && (LATITUDE_REF != null)
                && (LONGITUDE != null)
                && (LONGITUDE_REF != null)) {

            if (LATITUDE_REF.equals("N")) {
                Latitude = convertToDegree(LATITUDE);
            } else {
                Latitude = 0 - convertToDegree(LATITUDE);
            }

            if (LONGITUDE_REF.equals("E")) {
                Longitude = convertToDegree(LONGITUDE);
            } else {
                Longitude = 0 - convertToDegree(LONGITUDE);
            }

        }

        return new LatLng(Latitude, Longitude);
    }

    public static Float convertToDegree(String stringDMS){
        Float result = null;
        String[] DMS = stringDMS.split(",", 3);

        String[] stringD = DMS[0].split("/", 2);
        Double D0 = new Double(stringD[0]);
        Double D1 = new Double(stringD[1]);
        Double FloatD = D0/D1;

        String[] stringM = DMS[1].split("/", 2);
        Double M0 = new Double(stringM[0]);
        Double M1 = new Double(stringM[1]);
        Double FloatM = M0/M1;

        String[] stringS = DMS[2].split("/", 2);
        Double S0 = new Double(stringS[0]);
        Double S1 = new Double(stringS[1]);
        Double FloatS = S0/S1;

        result = new Float(FloatD + (FloatM/60) + (FloatS/3600));

        return result;


    };




    public int getLatitudeE6(Float Latitude){
        return (int)(Latitude*1000000);
    }

    public int getLongitudeE6(Float Longitude){
        return (int)(Longitude*1000000);
    }
}
