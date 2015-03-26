package uk.ac.aston.smalljh.wego.fragments.places;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import uk.ac.aston.smalljh.wego.ImagePreview;
import uk.ac.aston.smalljh.wego.ImageTagActivity;
import uk.ac.aston.smalljh.wego.MapPane;
import uk.ac.aston.smalljh.wego.PlaceItem;
import uk.ac.aston.smalljh.wego.PlaceViewActivity;
import uk.ac.aston.smalljh.wego.R;
import uk.ac.aston.smalljh.wego.fragments.places.PlacesFrag;
import uk.ac.aston.smalljh.wego.utils.DatabaseHelper;
import uk.ac.aston.smalljh.wego.utils.GPlaces;
import uk.ac.aston.smalljh.wego.utils.ImageItem;

/**
 * Created by joshuahugh on 23/03/15.
 */
public class PlacesGalleryFragment extends PlacesFrag {



    @Override
    public String getName() {
        return "Gallery";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gallery, container, false);

        DatabaseHelper dh = new DatabaseHelper(getActivity().getApplicationContext());

        ArrayList<ImageItem> images = placeItem.getImages();

        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
        GridViewAdapter customGridAdapter = new GridViewAdapter(getActivity().getApplicationContext(), R.layout.gallery_item, images);
        gridView.setAdapter(customGridAdapter);

        Button addPhoto = (Button) rootView.findViewById(R.id.add_pic_button);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });

        LinearLayout l = (LinearLayout) rootView.findViewById(R.id.add_pic_layout);
        l.setVisibility(View.VISIBLE);

        getActivity().setTitle(R.string.gallery);
        return rootView;
    }

    private class GridViewAdapter extends ArrayAdapter<ImageItem> {
        private Context context;
        private int layoutResourceId;
        private ArrayList<ImageItem> data = new ArrayList<ImageItem>();

        public GridViewAdapter(Context context, int layoutResourceId,
                               ArrayList<ImageItem> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new ViewHolder();
                holder.imageTitle = (TextView) row.findViewById(R.id.text);
                holder.image = (ImageView) row.findViewById(R.id.image);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            final ImageItem item = data.get(position);
            holder.imageTitle.setText(item.getTitle());
            holder.image.setImageBitmap(item.getImage());
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), ImagePreview.class);
                    intent.putExtra("ImageID", item.getID());
                    startActivity(intent);
                }
            });

            return row;
        }

        class ViewHolder {
            TextView imageTitle;
            ImageView image;
        }
    }


    protected void getImage() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.picture_type)
                .setMessage(R.string.picture_type_title)
                .setPositiveButton(R.string.picture_type_camera, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), PlaceViewActivity.class);

                        intent.putExtra("Value", PlaceViewActivity.REQUEST_IMAGE_CAPTURE);
                        intent.putExtra("Place", placeItem.getID());
                        startActivity(intent);

                        getActivity().finish();
                    }
                })
                .setNegativeButton(R.string.picture_type_storage, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), PlaceViewActivity.class);

                        intent.putExtra("Value", PlaceViewActivity.SELECT_PICTURE);
                        intent.putExtra("Place", placeItem.getID());
                        startActivity(intent);

                        getActivity().finish();
                    }
                }).show();




    }




    }


