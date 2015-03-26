package uk.ac.aston.smalljh.wego;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aston.smalljh.wego.utils.DatabaseHelper;
import uk.ac.aston.smalljh.wego.utils.ImageItem;


public class ImagePreview extends ActionBarActivity {

    private ImageItem image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view);

        long imageID = getIntent().getLongExtra("ImageID", 0);

        DatabaseHelper dh = new DatabaseHelper(getApplicationContext());

        image = dh.getImage(imageID);

        ImageView imageView = (ImageView) findViewById(R.id.image_tag_image);

        imageView.setImageBitmap(image.getImage());

        TextView tag = (TextView) findViewById(R.id.image_tag_tag);

        tag.setText("Tag:" + image.getTitle());

        TextView location = (TextView) findViewById(R.id.image_tag_location);
        location.setText("Location: " + image.getLatitude() + "," + image.getLatitude());

        Button close = (Button) findViewById(R.id.image_tag_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setTitle("Image: " + image.getTitle());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.menu_item_share) {

            Intent sharingIntent = new Intent();
            sharingIntent.setAction(Intent.ACTION_SEND);
            sharingIntent.setType("image/*");

            String pathofBmp = MediaStore.Images.Media.insertImage(getContentResolver(), image.getImage(), image.getTitle(), null);
                Uri bmpUri = Uri.parse(pathofBmp);

            sharingIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);

            startActivity(Intent.createChooser(sharingIntent, "Share image using"));
        }

        return super.onOptionsItemSelected(item);
    }

}
